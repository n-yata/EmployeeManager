package beans;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Photo {
	private int id = 0;
	private String contentType = "";
	private byte[] photo;
	public static final long MAX_FILE_SIZE = 2000000;

	public Photo() {}

	public Photo(String contentType, byte[] photo) {
		this.contentType = contentType;
		this.photo = photo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	//オーバーロード
	public void setPhoto(InputStream in) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            byte[] buffer = new byte[1024];
            while(true) {
                int len = in.read(buffer);
                if(len < 0) {
                    break;
                }
                out.write(buffer, 0, len);
            }
             this.photo = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
