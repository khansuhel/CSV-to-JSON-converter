import builder.JsonBuilder;
import com.google.gson.JsonObject;
import java.util.Map;
import model.FieldProperty;
import util.MockFieldPropertiesUtil;
import util.Reader;

public class App
{
    public static void main(String[] args)
    {
        Map<String, FieldProperty> fieldProperties = MockFieldPropertiesUtil.getFieldProperties();
        try(Reader reader = new Reader()){
            String[] header = reader.readNext();
            JsonBuilder builder = new JsonBuilder(fieldProperties, header);
            String[] line;
            line=reader.readNext();
            JsonObject previous = builder.build(null, line);
            line = reader.readNext();
            JsonObject finalJson  = builder.build(previous, line);
            System.out.println(finalJson);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
