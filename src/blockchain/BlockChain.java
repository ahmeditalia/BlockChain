package blockchain;

import java.util.ArrayList;

public class BlockChain {
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 1;

	public static Block addBlock() {
		String data = "";
		for(String s:Vote.Data) {
			data+=s;
		}
		String previousHash = blockchain.size() == 0 ? "0" : blockchain.get(blockchain.size() - 1).getHash();
		Block block = new Block(data, previousHash, difficulty);
		System.out.println("-----------------------------------------------------------------");
		System.out.println("mined block : \n"+block.toString());
		if(!blockchain.contains(block))
		{
			blockchain.add(block);
		}
		Vote.Data.clear();
		return block;
	}

	public static ArrayList<Block> getBlockchain() {
		return blockchain;
	}

	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');

		// loop through blockchain to check hashes:
		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			// compare registered hash and calculated hash:
			if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
				System.out.println("Current Hashes not equal");
				return false;
			}
			// compare previous hash and registered previous hash
			if (!previousBlock.hash.equals(currentBlock.previousHash)) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			// check if hash is solved
			if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
		}
		return true;
	}
}
