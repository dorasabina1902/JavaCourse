package dao.commonDao;

import java.util.ArrayList;

import model.Admin;

public interface DaoAdmin {

	// Main CRUD for admin
	int addAdmin(Admin adm);

	ArrayList<Admin> getAllAdmins();

	int updateAdmin(Admin adm);

	int deleteAdmin(int id);

	// As Required for admin
	Admin getAdminById(int id);

	Admin getAdminByContact(String contact);

	ArrayList<String> selectRoleNames();

	int getRoleIdByRoleName(String roleName);

	String getRoleNameByRoleId(int roleId);

	boolean checkDuplicateAdmin(String firstName, String contactMe, String email);

	boolean checkDuplicateAdminOnEdit(int id, String contactMe, String email);
}
