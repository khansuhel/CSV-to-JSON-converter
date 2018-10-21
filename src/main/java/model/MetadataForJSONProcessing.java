package model;

import java.util.LinkedHashMap;
import java.util.Map;

public class MetadataForJSONProcessing
{
    // Key: Individual element, Value: Corresponding parent
    private Map<String, String> parentChain;
    private String fieldName;

    public MetadataForJSONProcessing()
    {
        this.parentChain = new LinkedHashMap<>();
    }


    public String getFieldName()
    {
        return fieldName;
    }


    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }


    public Map<String, String> getParentChain()
    {
        return parentChain;
    }


    public void setParentChain(Map<String, String> parentChain)
    {
        this.parentChain = parentChain;
    }
}
