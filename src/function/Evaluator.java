package function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Evaluator {
    private static HashMap<String, String> functionMap;
    static{
        functionMap.put("sum", "Sum");
        functionMap.put("average", "Average");
        functionMap.put("count", "Count");
        functionMap.put("countblank", "CountBlank");
        functionMap.put("var", "Variance");
        functionMap.put("stddev", "StandardDeviation");
        functionMap.put("min", "Minimum");
        functionMap.put("max", "Maximum");
    }
    protected static void addFunction(String key, String functionName){
        functionMap.put(key, functionName);
    }
    private static List<String> tokenize(String expression){
        return Arrays.asList(
                expression.split("(?=[-+*/^()\"<]|(?<!<)>|(?<![<>])=)|(?<=>(?!=)|=|<(?![>=])|[^-+*/^,(=><][-+]|[\"*/^,()])"));
    }
    private static List<String> toPostfix(List<String> infix){
        for(String s : infix){

        }
        return null;
    }
    public static DataType evaluate(String expression){
        ArrayList<String> tokens = (ArrayList<String>) tokenize(expression);

        return null;
    }
}
