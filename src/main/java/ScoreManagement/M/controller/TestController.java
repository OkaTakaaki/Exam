package ScoreManagement.M.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ScoreManagement.M.model.StudentModel;
import ScoreManagement.M.model.SubjectModel;
import ScoreManagement.M.model.TeacherModel;
import ScoreManagement.M.model.TestModel;
import ScoreManagement.M.repository.StudentRepository;
import ScoreManagement.M.repository.SubjectRepository;
import ScoreManagement.M.repository.TestRepository;
import ScoreManagement.M.service.StudentService;
import ScoreManagement.M.service.SubjectService;
import ScoreManagement.M.service.TestService;

 
@Controller
public class TestController {
	@Autowired
	private TestService testService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private TestRepository testRepository;
 
 
		@GetMapping("/test/")
		public String index(Model model, @AuthenticationPrincipal UserDetails user) {
			model.addAttribute("list", this.testService.getResTestList(user));
			model.addAttribute("subjectlist", this.subjectService.getAll());
			return "testform";
		}
		
		
		
		@PostMapping("/test/")
	    public String getFilteredTests(
	    		@RequestParam(name = "entYear", required = false) Integer entYear,
	            @RequestParam(name = "classNum", required = false) String classNum,
	            Model model, @AuthenticationPrincipal TeacherModel teacher) {
	        // 検索操作の場合
	        List<StudentModel> tests = testService.searchTests(entYear, classNum, teacher.getSchoolCd());
	        model.addAttribute("list", tests);
	        return "testform";
	    }
		
		@PostMapping("/testadd/")
		public String add(@Validated @ModelAttribute TestModel test, RedirectAttributes redirectAttributes,
				ModelAndView model, @AuthenticationPrincipal UserDetails user,
				@RequestParam(name = "point", required = false) Integer point[],
				@RequestParam(name = "no", required = false) Integer no){
				System.out.println(test);
				String sno[] = test.getStudentNo().split(",");
				String scd = test.getSubjectCd();
				String cnum[] = test.getClassNum().split(",");
				System.out.println("----------------");
				System.out.println(test);
				System.out.println(point[1]);
				System.out.println("----------------");
				
				for (int i = 0; i < sno.length; i++) {
					//testModel型をインスタンス化する
					TestModel tests = new TestModel();
					tests.setStudentNo(sno[i]);
					tests.setSubjectCd(scd);
					tests.setNo(no);
					tests.setPoint(point[i]);
					tests.setClassNum(cnum[i]);
					this.testService.save(tests, user);
				}
	
			return "testcomplate";
		}
		
		
		@GetMapping("/testlookup/")
		public String testlookup(Model model, @AuthenticationPrincipal UserDetails user) {
			model.addAttribute("list", this.testService.getResTestList(user));
			model.addAttribute("kamokulist", this.subjectService.getAll());
			model.addAttribute("studentlist", this.studentService.getAll());
			return "testreference";
		}
		
		@PostMapping("/testlookup/subjects/")
	    public String getFilteredReferences(
	    		@RequestParam(name = "entYear", required = false) Integer entYear,
	            @RequestParam(name = "classNum", required = false) String classNum,
	            @RequestParam(name = "subjectCd", required = false) String subjectCd,
	            Model model) {
	        // 検索操作の場合
			
	        
			//listという名前は、controllerの@GetMapping("/test/reference/")　と　templatesのstudentのth:each="item, stat : ${list}"　と同じにする
	        List<TestModel> references = testService.searchReferences(entYear, classNum, subjectCd);
	        
	        List<Integer> entYears = new ArrayList<>(); 
	        for (int i = 0; i < references.size(); i++) {
	        	entYears.add(entYear);
	        }
	        
	        System.out.println("----" + entYears+ "---");
            System.out.println("----" + references+ "---");
            model.addAttribute("entYearslist", entYears);
	        model.addAttribute("subjectslist", references);
	        return "testreference";
	    }
		

		@PostMapping("/testlookup/studentnos/")
	    public String getFilteredStudentNos(
	            @RequestParam(name = "studentNo", required = false) String studentNo,
	            Model model) {
	        // 検索操作の場合
	        List<TestModel> studentNos = testService.searchStudentNos(studentNo);
	        System.out.println(studentNo);
	        System.out.println("-----------------");
	        System.out.println(studentNos);
	        
	        List<String> subjectCds = new ArrayList<>(); // SubjectCd の数値を格納するリストを作成
	        for (TestModel testModel : studentNos) {
	            subjectCds.add(testModel.getSubjectCd()); // 各 TestModel オブジェクトから SubjectCd の数値を取得し、リストに追加
	        }
	        System.out.println("StudentModelのsubjectCd =" + subjectCds);
	        
	        List<String> subjectNames = new ArrayList<>(); 
	        for (String kariname : subjectCds) {
	        	SubjectModel sub = this.subjectRepository.findByCd(kariname);
	        	//subjectNames.add(sub); 
	        	System.out.println("kariname" + kariname);
	        	System.out.println(sub);
	        	subjectNames.add(sub.getName());
	        }
	        System.out.print(subjectNames);
	        
	        model.addAttribute("subjectNameslist", subjectNames);
	        model.addAttribute("studentNoslist", studentNos);
			//listという名前は、controllerの@GetMapping("/test/reference/")　と　templatesのstudentのth:each="item, stat : ${list}"　と同じにする
	        return "testreference";
	    }
		
}