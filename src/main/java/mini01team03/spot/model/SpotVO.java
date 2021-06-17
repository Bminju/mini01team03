package mini01team03.spot.model;

import lombok.Data;
//혜지추가
//db에서 장소 정보를 가져오기 위한 테스트용
@Data
public class SpotVO {
	private String lo_name;
	private String address;
	private String cate;

}
