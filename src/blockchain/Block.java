package blockchain;

import java.security.MessageDigest;
import java.util.Date;

public class Block {
	public String hash;
	public String previousHash;
	public String getPreviousHash() {
		return previousHash;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	private String data;
	private int nonce;

	public Block(String hash, String previousHash, String data, int nonce) {
		super();
		this.hash = hash;
		this.previousHash = previousHash;
		this.data = data;
		this.nonce = nonce;
	}

	public Block(String data, String previousHash, int difficulty) {
		this.data = data;
		this.previousHash = previousHash;
		this.nonce = 0;
		mineBlock(difficulty);
	}

	public Block(String data) {
		String[] strings = data.split(",");
		this.hash = strings[0];
		this.previousHash = strings[1];
		this.data = strings[2];
		this.nonce = Integer.parseInt(strings[3]);
	}

	public String calculateHash() {
		String calculatedhash = applySha256(previousHash + Integer.toString(nonce) + data);
		return calculatedhash;
	}

	public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0'); // Create a string with difficulty * "0"
		hash = calculateHash();
		while (!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}

	public static String applySha256(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			// Applies sha256 to our input,
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getHash() {
		return hash;
	}

	@Override
	public String toString() {
		String ret = hash + "," + previousHash + "," + data  + ","
				+ Integer.toString(nonce);
		return ret;
	}
}
