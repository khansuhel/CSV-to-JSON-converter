package util;

import java.util.HashMap;
import java.util.Map;
import model.DataType;
import model.FieldProperty;

public class MockFieldPropertiesUtil
{
    public static Map<String, FieldProperty> getFieldProperties()
    {

        Map<String, FieldProperty> map = new HashMap<>();
        try (Reader reader = new Reader())
        {
            //String[] header = reader.readNext();

            map.put("sPkey", new FieldProperty(null, "sPkey", DataType.TEXT, 1));
            map.put("name", new FieldProperty(null, "name", DataType.TEXT, 1));
            map.put("age", new FieldProperty(null, "age", DataType.NUMBER, 1));
            map.put("Address", new FieldProperty("id", "Address", DataType.NOTYPE, -1));
            map.put("Address.id", new FieldProperty(null, "Address.id", DataType.TEXT, 1));
            map.put("Address.Phone", new FieldProperty("id", "Address.Phone", DataType.NOTYPE, -1));
            map.put("Address.Phone.id", new FieldProperty(null, "Address.Phone.id", DataType.NUMBER, 1));
            map.put("Address.Phone.number", new FieldProperty(null, "Address.Phone.number", DataType.NUMBER, 1));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return map;
    }
}
