package entity;

import java.io.Serializable;

public class TrainJourneyDetails implements Serializable {
	private String maChuyenTau;
	private String soHieuTau;
	private String tenChuyenTau;
	private String hanhTrinh;
	private String ngayKhoiHanh;
	private String thoiGian;
	private double cuLy;
	private String tongHanhKhach;

	public TrainJourneyDetails(String maChuyenTau, String soHieuTau, String tenChuyenTau, String hanhTrinh,
			String ngayKhoiHanh, String thoiGian, double cuLy, String tongHanhKhach) {
		super();
		this.maChuyenTau = maChuyenTau;
		this.soHieuTau = soHieuTau;
		this.tenChuyenTau = tenChuyenTau;
		this.hanhTrinh = hanhTrinh;
		this.ngayKhoiHanh = ngayKhoiHanh;
		this.thoiGian = thoiGian;
		this.cuLy = cuLy;
		this.tongHanhKhach = tongHanhKhach;
	}

	public String getMaChuyenTau() {
		return maChuyenTau;
	}

	public void setMaChuyenTau(String maChuyenTau) {
		this.maChuyenTau = maChuyenTau;
	}

	public String getSoHieuTau() {
		return soHieuTau;
	}

	public void setSoHieuTau(String soHieuTau) {
		this.soHieuTau = soHieuTau;
	}

	public String getTenChuyenTau() {
		return tenChuyenTau;
	}

	public void setTenChuyenTau(String tenChuyenTau) {
		this.tenChuyenTau = tenChuyenTau;
	}

	public String getHanhTrinh() {
		return hanhTrinh;
	}

	public void setHanhTrinh(String hanhTrinh) {
		this.hanhTrinh = hanhTrinh;
	}

	public String getNgayKhoiHanh() {
		return ngayKhoiHanh;
	}

	public void setNgayKhoiHanh(String ngayKhoiHanh) {
		this.ngayKhoiHanh = ngayKhoiHanh;
	}

	public String getThoiGian() {
		return thoiGian;
	}

	public void setThoiGian(String thoiGian) {
		this.thoiGian = thoiGian;
	}

	public double getCuLy() {
		return cuLy;
	}

	public void setCuLy(double cuLy) {
		this.cuLy = cuLy;
	}

	public String getTongHanhKhach() {
		return tongHanhKhach;
	}

	public void setTongHanhKhach(String tongHanhKhach) {
		this.tongHanhKhach = tongHanhKhach;
	}

	@Override
	public String toString() {
		return "TrainJourneyDetails [maChuyenTau=" + maChuyenTau + ", soHieuTau=" + soHieuTau + ", tenChuyenTau="
				+ tenChuyenTau + ", hanhTrinh=" + hanhTrinh + ", ngayKhoiHanh=" + ngayKhoiHanh + ", thoiGian="
				+ thoiGian + ", cuLy=" + cuLy + ", tongHanhKhach=" + tongHanhKhach + "]";
	}

}
