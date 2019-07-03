package dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import beans.Photo;

public class test {
	public static byte[] convertFile(File file) {
        try (FileInputStream inputStream = new FileInputStream(file);) {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            while(true) {
                int len = inputStream.read(buffer);
                if(len < 0) {
                    break;
                }
                bout.write(buffer, 0, len);
            }
            return bout.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
    }

	public static void main(String[] args) {
		File file = new File("GettyImages-522585140.jpg");
		Photo photo = new Photo();
		photo.setContentType("image/jpg");
		photo.setPhoto(convertFile(file));
//		new PhotoDAO().insert(photo);
//		EmployeeDAO dao = new EmployeeDAO();
//		Employee emp = new Employee();
//		emp.setEmpId("1");
//		emp.setName("aaa");
//		emp.setAge(11);
//		emp.setPostId(1);
//		if(dao.insert(emp)) {
//			System.out.println("データの更新に成功しました");
//		}else {
//			System.out.println("失敗しました");
//		}
//	}
//		Post post = new Post();
//		post.setName("総務部");
//		new PostDAO().insert(post);

//		ArrayList<Employee> employees = new EmployeeDAO().searchEmp(0, null, null);
//		for(Employee employee : employees) {
//			System.out.println(employee.toString());
//		}




	}
}
