package com.ascent.util;

import java.util.*;
import java.io.*;
import com.ascent.bean.Product;
import com.ascent.bean.User;

/**
 * ��Ʒ���ݶ�ȡ��ʵ����
 * @author ascent
 * @version 1.0
 */
public class ProductDataAccessor extends DataAccessor {

	// ////////////////////////////////////////////////////
	//
	// ��Ʒ�ļ���ʽ����
	// ��Ʒ����,��ѧ��ժ�ǼǺ�,�ṹͼ,��ʽ,�۸�,����,���
	// ----------------------------------------------------
	//
	// ////////////////////////////////////////////////////

	// ////////////////////////////////////////////////////
	//
	// �û��ļ���ʽ����
	// �û��ʺ�,�û�����,�û�Ȩ��
	// ----------------------------------------------------
	//
	// ////////////////////////////////////////////////////
	/**
	 * ��Ʒ��Ϣ�����ļ���
	 */
	protected static final String PRODUCT_FILE_NAME = "product.db";

	/**
	 * �û���Ϣ�����ļ���
	 */
	protected static final String USER_FILE_NAME = "user.db";

	/**
	 * ���ݼ�¼�ķָ��
	 */
	protected static final String RECORD_SEPARATOR = "----------";

	/**
	 * Ĭ�Ϲ��췽��
	 */
	public ProductDataAccessor() {
		load();
	}

	/**
	 * ��ȡ���ݵķ���
	 */
	@Override
	public void load() {

		dataTable = new HashMap<String,ArrayList<Product>>();
		userTable = new HashMap<String,User>();

		ArrayList<Product> productArrayList = null;
		StringTokenizer st = null;

		Product productObject = null;
		User userObject = null;
		String line = "";

		String productName, cas, structure, formula, price, realstock, category;
		String userName, password, authority;

		try {
			log("��ȡ�ļ�: " + PRODUCT_FILE_NAME + "...");
			BufferedReader inputFromFile1 = new BufferedReader(new FileReader(PRODUCT_FILE_NAME));

			while ((line = inputFromFile1.readLine()) != null) {

				st = new StringTokenizer(line, ",");

				productName = st.nextToken().trim();
				cas = st.nextToken().trim();
				structure = st.nextToken().trim();
				formula = st.nextToken().trim();
				price = st.nextToken().trim();
				realstock = st.nextToken().trim();
				category = st.nextToken().trim();

				productObject = getProductObject(productName, cas, structure,formula, price, realstock, category);

				if (dataTable.containsKey(category)) {
					productArrayList = dataTable.get(category);
				} else {
					productArrayList = new ArrayList<Product>();
					dataTable.put(category, productArrayList);
				}
				productArrayList.add(productObject);
			}

			inputFromFile1.close();
			log("�ļ���ȡ����!");

			line = "";
			log("��ȡ�ļ�: " + USER_FILE_NAME + "...");
			BufferedReader inputFromFile2 = new BufferedReader(new FileReader(USER_FILE_NAME));
			while ((line = inputFromFile2.readLine()) != null) {

				st = new StringTokenizer(line, ",");

				userName = st.nextToken().trim();
				password = st.nextToken().trim();
				authority = st.nextToken().trim();
				userObject = new User(userName, password, Integer.parseInt(authority));

				if (!userTable.containsKey(userName)) {
					userTable.put(userName, userObject);
				}
			}

			inputFromFile2.close();
			log("�ļ���ȡ����!");
			log("׼������!\n");
		} catch (FileNotFoundException exc) {
			log("û���ҵ��ļ�: " + PRODUCT_FILE_NAME + " �� "+USER_FILE_NAME+".");
			log(exc);
		} catch (IOException exc) {
			log("��ȡ�ļ������쳣: " + PRODUCT_FILE_NAME+ " �� "+USER_FILE_NAME+".");
			log(exc);
		}
	}

	/**
	 * ���ش�����Щ��������Ʒ����
	 * @param productName ҩƷ����
	 * @param cas ��ѧ��ժ�ǼǺ�
	 * @param structure �ṹͼ����
	 * @param formula ��ʽ
	 * @param price �۸�
	 * @param realstock ����
	 * @param category ���
	 * @return new Product(productName, cas, structure, formula, price, realstock, category);
	 */
	private Product getProductObject(String productName, String cas,
			String structure, String formula, String price, String realstock, String category) {
		return new Product(productName, cas, structure, formula, price, realstock, category);
	}

	/**
	 * ��������
	 */
	@Override
	public void save(User user) {
		log("��ȡ�ļ�: " + USER_FILE_NAME + "...");
		try {
			String userinfo = user.getUsername() + "," + user.getPassword() + "," + user.getAuthority();
			RandomAccessFile fos = new RandomAccessFile(USER_FILE_NAME, "rws");
			fos.seek(fos.length());
			fos.write(("\n" + userinfo).getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��־����.
	 */
	@Override
	protected void log(Object msg) {
		System.out.println("ProductDataAccessor��: " + msg);
	}

	@Override
	public HashMap<String,User> getUsers() {
		this.load();
		return this.userTable;
	}

	//v1.0.1
	// ... ԭ�д��벻��

	/**
	 * ���ݲ�Ʒ����ɾ����Ʒ
	 * @param productName Ҫɾ���Ĳ�Ʒ����
	 * @return boolean ɾ���ɹ�����true�����򷵻�false
	 */
	public boolean deleteProduct(String productName) {
		// ����dataTable���ҵ���Ӧ�ķ���Ͳ�Ʒ
		for (Map.Entry<String, ArrayList<Product>> entry : dataTable.entrySet()) {
			ArrayList<Product> list = entry.getValue();
			Product toRemove = null;
			for (Product p : list) {
				if (p.getProductname().equalsIgnoreCase(productName)) {
					toRemove = p;
					break;
				}
			}
			if (toRemove != null) {
				list.remove(toRemove);
				saveAllProducts(); // ɾ���󱣴�
				return true;
			}
		}
		return false;
	}

	/**
	 * ���²�Ʒ��Ϣ�����ݲ�Ʒ���Ʋ��Ҳ�Ʒ��Ȼ�����������Ϣ��
	 * @param updatedProduct ��������Ϣ�Ĳ�Ʒ�������ơ�CAS������ԭ��Ʒһ�²����ҵ������£�
	 * @return boolean ���³ɹ�����true�����򷵻�false
	 */
	public boolean updateProduct(Product updatedProduct) {
		for (Map.Entry<String, ArrayList<Product>> entry : dataTable.entrySet()) {
			ArrayList<Product> list = entry.getValue();
			for (int i = 0; i < list.size(); i++) {
				Product p = list.get(i);
				if (p.getProductname().equalsIgnoreCase(updatedProduct.getProductname())) {
					// �ҵ���Ʒ����������Ϣ
					list.set(i, updatedProduct);
					saveAllProducts(); // ���º󱣴�
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * ����ǰdataTable�еĲ�Ʒ��Ϣȫ��д�ص�PRODUCT_FILE_NAME�ļ���
	 */
	public void saveAllProducts() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCT_FILE_NAME))) {
			for (Map.Entry<String, ArrayList<Product>> entry : dataTable.entrySet()) {
				for (Product p : entry.getValue()) {
					String line = p.getProductname() + "," + p.getCas() + "," + p.getStructure() + ","
							+ p.getFormula() + "," + p.getPrice() + "," + p.getRealstock() + "," + p.getCategory();
					writer.write(line);
					writer.newLine();
				}
			}
			writer.flush();
		} catch (IOException e) {
			log("�����Ʒ����ʱ�����쳣:" + e);
		}
	}

}