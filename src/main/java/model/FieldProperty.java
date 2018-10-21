package model;

public class FieldProperty
{
    private String name;
    private DataType dataType;
    private int cardinality;
    private String idField;


    public FieldProperty(String idField, String name, DataType dataType, int cardinality)
    {
        this.idField = idField;
        this.name = name;
        this.dataType = dataType;
        this.cardinality = cardinality;
    }


    public String getIdField()
    {
        return idField;
    }


    public void setIdField(String idField)
    {
        this.idField = idField;
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public DataType getDataType()
    {
        return dataType;
    }


    public void setDataType(DataType dataType)
    {
        this.dataType = dataType;
    }


    public int getCardinality()
    {
        return cardinality;
    }


    public void setCardinality(int cardinality)
    {
        this.cardinality = cardinality;
    }
}
