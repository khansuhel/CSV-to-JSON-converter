package builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import model.FieldProperty;
import model.MetadataForJSONProcessing;

public class JsonBuilder
{
    private Map<String, FieldProperty> fieldProperties;
    private String[] header;
    private MetadataForJSONProcessing[] metadataForJsonProcessingArray;
    private Map<String, Integer> idFieldColumnIndexMap;


    public JsonBuilder(Map<String, FieldProperty> fieldProperties, String[] header)
    {
        this.fieldProperties = fieldProperties;
        this.header = header;
        this.metadataForJsonProcessingArray = new MetadataForJSONProcessing[header.length];
        this.idFieldColumnIndexMap = new LinkedHashMap<>();
        initMetadataForJsonProcessing();
    }


    private void initMetadataForJsonProcessing()
    {
        int index = 0;
        MetadataForJSONProcessing metadataForJSONProcessing;
        for (String s : header)
        {
            if (s.contains("."))
            {
                metadataForJSONProcessing = new MetadataForJSONProcessing();
                Map<String, String> mapOfFieldAndParent = new LinkedHashMap<>();
                String[] temp = s.split("[.]");
                String fieldName = temp[temp.length - 1];
                String key, value = null;
                for (int i = 0; i < temp.length - 1; i++)
                {
                    key = temp[i];
                    value = temp[0];
                    for (int j = 1; j <= i; j++)
                    {
                        value = value.concat(".").concat(temp[j]);
                    }
                    mapOfFieldAndParent.put(key, value);
                }
                if (this.fieldProperties.get(value).getIdField().equalsIgnoreCase(fieldName))
                {
                    idFieldColumnIndexMap.put(value, index);
                }
                metadataForJSONProcessing.setParentChain(mapOfFieldAndParent);
                metadataForJSONProcessing.setFieldName(fieldName);

                metadataForJsonProcessingArray[index] = metadataForJSONProcessing;
            }
            index++;
        }
    }


    public JsonObject build(JsonObject previous, String[] currentDataLine)
    {
        JsonObject parentJson;
        if (null == previous || !previous.get("sPkey").getAsString().equalsIgnoreCase(currentDataLine[0]))
        {
            sendToKafka(previous);
            parentJson = new JsonObject();
        }
        else
        {
            parentJson = previous;
        }

        int index = 0;
        for (String s : currentDataLine)
        {
            if (metadataForJsonProcessingArray[index] == null)
            {
                addField(parentJson, header[index], s);
            }
            else
            {
                JsonElement node = parentJson;

                Map<String, String> parentChain = metadataForJsonProcessingArray[index].getParentChain();
                String fieldName = metadataForJsonProcessingArray[index].getFieldName();

                Iterator<Map.Entry<String, String>> it = parentChain.entrySet().iterator();
                while (it.hasNext())
                {
                    Map.Entry<String, String> entry = it.next();
                    String key = entry.getKey().trim();
                    String value = entry.getValue().trim();

                    if (!node.getAsJsonObject().has(key))
                    {
                        node.getAsJsonObject().add(key, fieldProperties.get(value).getCardinality() == -1 ? new JsonArray() : new JsonObject());
                    }
                    node = node.getAsJsonObject().get(key.trim());
                    if (node.isJsonArray())
                    {
                        node = getJsonObjectFromArray(node.getAsJsonArray(), key, fieldProperties.get(value).getIdField(),
                            currentDataLine[idFieldColumnIndexMap.get(value).intValue()]);
                    }
                }
                addField(node, fieldName, currentDataLine[index]);
            }
            index++;
        }
        return parentJson;
    }


    JsonObject getJsonObjectFromArray(JsonArray array, String key, String idFieldName, String idFieldValue)
    {
        for (JsonElement element : array)
        {
            if (element.getAsJsonObject().get(idFieldName).getAsString().equalsIgnoreCase(idFieldValue))
            {
                return element.getAsJsonObject();
            }
        }
        JsonObject newObject = new JsonObject();
        array.add(newObject);
        return newObject;
    }


    JsonObject addField(JsonElement input, String key, String value)
    {
        JsonObject object = null;
        if (input.isJsonArray())
        {
            if (input.getAsJsonArray().size() == 0)
            {
                object = new JsonObject();
                object.addProperty(key, value);
                input.getAsJsonArray().add(object);
            }
            else
            {
                for (JsonElement element : input.getAsJsonArray())
                {
                    if (element.getAsJsonObject().get("id").getAsString().equalsIgnoreCase(value))
                    {

                    }
                }
                object = input.getAsJsonArray().get(0).getAsJsonObject();
                object.addProperty(key, value);
            }
        }
        else if (input.isJsonObject())
        {
            if (!input.getAsJsonObject().has(key))
            {
                input.getAsJsonObject().addProperty(key, value);
            }
        }
        return object;
    }


    void sendToKafka(JsonObject previous)
    {
        // send to Kafka
    }

}
