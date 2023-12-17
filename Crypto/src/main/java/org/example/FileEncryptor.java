import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

public class FileEncryptor {

    private String key;

    public void setKey(String key) {
        this.key = key;
    }

    public static void encryptFile(String inputFile, String key) throws Exception {
        Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        File originalFile = new File(inputFile);
        String encryptedFilePath = originalFile.getParent() + File.separator + "Encrypted_" + originalFile.getName();

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(encryptedFilePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] encryptedBytes = cipher.update(buffer, 0, bytesRead);
                outputStream.write(encryptedBytes);
            }

            byte[] encryptedBytes = cipher.doFinal();
            outputStream.write(encryptedBytes);

        }
    }

    public static void decryptFile(String inputFile, String key) throws Exception {
        Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        File originalFile = new File(inputFile);
        String decryptedFilePath = originalFile.getParent() + File.separator + "Decrypted_" + originalFile.getName();

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(decryptedFilePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] decryptedBytes = cipher.update(buffer, 0, bytesRead);
                outputStream.write(decryptedBytes);
            }

            byte[] decryptedBytes = cipher.doFinal();
            outputStream.write(decryptedBytes);

        }
    }
}
