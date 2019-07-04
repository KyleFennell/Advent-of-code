import java.security.MessageDigest;

public class Main {
    public static void main(String args[]){
        MessageDigest md5 = null;
        try {md5 = MessageDigest.getInstance("MD5");}
        catch (Exception e){}
        String prefix = "yzbqklnj";
        String output = "     ";
        int counter = 0;
        while(!output.substring(0, 5).equals("00000")){
            output = byteArrayToHex(md5.digest((prefix + counter).getBytes()));
            counter++;
        }
        System.out.println(output+" "+counter);
        while(!output.substring(0, 6).equals("000000")){
            output = byteArrayToHex(md5.digest((prefix + counter).getBytes()));
            counter++;
        }
        System.out.println(output+" "+counter);
    }
    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
