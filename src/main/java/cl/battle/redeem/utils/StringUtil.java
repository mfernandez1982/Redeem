package cl.battle.redeem.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Kyle
 * Date: 23/01/13
 * Time: 6:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringUtil {

    public static boolean startsWithIgnoreCase(String strPrefix, String fullString)
    {
        String strPrefixU = strPrefix.toUpperCase();
        String strPrefixL = strPrefix.toLowerCase();

        if(fullString.startsWith(strPrefixU) || fullString.startsWith(strPrefixL)){
            return true;
        }
        return false;
    }
    
    public static String assembleString(String[] args)
    {
        String aStr = (args[2] != null) ? args[2] : "";
        if(args.length > 3){
            for(int i = 3; i < args.length; i++){
                if(aStr == ""){
                    aStr = args[i];
                }else{
                    aStr+= (args[i] != null)? (" " + args[i]): "";
                }

            }
        }
        return aStr;
    }
}
