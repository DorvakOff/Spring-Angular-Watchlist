package com.dorvak.webapp.moteur.security.keygen;

import com.dorvak.webapp.moteur.utils.FileUtils;
import com.dorvak.webapp.moteur.utils.LoggerUtils;
import com.dorvak.webapp.moteur.utils.PemUtils;

import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class KeyGenerator {

    public static final int KEY_SIZE = 2048;
    public static final String KEY_ALGORITHM = "RSA";
    public static final String PATH = "WEB-INF/security/keys";
    public static final String RSA_PUBLIC_PEM = "rsa-public.pem";
    public static final String RSA_PRIVATE_PEM = "rsa-private.pem";
    private File keyFolder;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public KeyGenerator(String name) throws IOException, NoSuchAlgorithmException {
        keyFolder = new File(PATH);
        if (!keyFolder.exists() && keyFolder.mkdirs()) {
            LoggerUtils.info("Created key folder");
        }
        keyFolder = new File(PATH, name);
        if (!keyFolder.exists() && keyFolder.mkdirs()) {
            LoggerUtils.info("Created key folder for %s", name);
        }
        initKeyPairs();
    }

    private void initKeyPairs() throws IOException, NoSuchAlgorithmException {
        if (FileUtils.fileExists(keyFolder.getPath(), RSA_PUBLIC_PEM) && FileUtils.fileExists(keyFolder.getPath(), RSA_PRIVATE_PEM)) {
            publicKey = PemUtils.readPublicKeyFromFile(keyFolder + File.separator + RSA_PUBLIC_PEM, KEY_ALGORITHM);
            privateKey = PemUtils.readPrivateKeyFromFile(keyFolder + File.separator + RSA_PRIVATE_PEM, KEY_ALGORITHM);
            LoggerUtils.info("KeyPair '%s' loaded", keyFolder.getName());
        } else {
            createKeys();
        }
    }

    private void generateFolderAndFiles() throws IOException {
        FileUtils.generateFile(keyFolder.getPath(), RSA_PUBLIC_PEM);
        FileUtils.generateFile(keyFolder.getPath(), RSA_PRIVATE_PEM);
    }

    public void createKeys() throws NoSuchAlgorithmException, IOException {
        generateFolderAndFiles();

        KeyPair pair = generateKeys();
        publicKey = pair.getPublic();
        privateKey = pair.getPrivate();

        saveKeyPair(pair);
        LoggerUtils.info("KeyPair '%s' created", keyFolder.getName());
    }

    private KeyPair generateKeys() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyGen.initialize(KEY_SIZE);
        return keyGen.generateKeyPair();
    }

    private void saveKeyPair(KeyPair pair) throws IOException {
        PemUtils.writePublicKeyToFile(keyFolder + File.separator + RSA_PUBLIC_PEM, pair.getPublic());
        PemUtils.writePrivateKeyToFile(keyFolder + File.separator + RSA_PRIVATE_PEM, pair.getPrivate());
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}