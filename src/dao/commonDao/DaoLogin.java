package dao.commonDao;

import model.Admin;
import model.Student;
import model.Teacher;

public interface DaoLogin {

	Student getStudentDetails(String email, String password);

	Teacher getTeacherDetails(String email, String password);

	Admin getAdminDetails(String email, String password);

}
