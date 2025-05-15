package fund_application;

import java.io.*;
import java.util.*;

public class Register_main {
	private static Scanner sc = new Scanner(System.in);
	private static final String FILE_NAME = ".users.txt";
	public String id; //회원가입-아이디
	public String password;
	public String name;
	public void Registermain() {
		
	while (true) {
	    System.out.println("[1] 로그인 [2] 회원가입 [3] 종료");
	    int choice = -1;
	    boolean validInput = false;
	    while (!validInput) {
	        try {
	            choice = sc.nextInt();
	            sc.nextLine(); // 버퍼 비우기
	            validInput = true;
	        } catch (Exception e) {
	            System.out.println("숫자를 입력하세요."); // 오류 메시지
	            sc.nextLine(); // 잘못된 입력 버퍼 비우기
	        }
	    }
        boolean log_in=false;

        switch (choice) {
            case 1:
                log_in=login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.out.println("프로그램을 종료합니다.");
                return;
            default:
                System.out.println("올바른 선택이 아닙니다.\n");
                break;

        }
        if(log_in) {
        	break;
        	
        }
    }
	}
    
  //회원가입 기능
    //사용자로부터 이름, 아이디, 비밀번호를 입력받음
	private static void register() {
			String name;
        while (true) {
            System.out.print("이름: ");
            name = sc.nextLine();
            if (name.trim().isEmpty()) { // 공백 포함 여부 검사
               System.out.println("이름을 입력해야 합니다.");
            }
            else if(name.contains(" ")) {
                System.out.println("이름에 공백을 포함하면 안됩니다.");
            }
            else {
                break;
            }
        }

        	String id;
        while (true) {
            System.out.print("아이디: ");
            id = sc.nextLine();
            if (id.trim().isEmpty()) {
                System.out.println("아이디를 입력해야 합니다");
            }
            else if(id.contains(" ")) {
                System.out.println("아이디에 공백을 포함하면 안됩니다");
            }
            else {
                if(availableId(id)) {
                    System.out.println("아이디 사용 가능");
                    break;
                }else {
                    System.out.println("중복된 아이디입니다.");
                }
            }
            
        }

        	String password;
        while (true) {
            System.out.println("(비밀번호는 8자 이상이어야 하며, 대문자, 소문자, 숫자를 최소 1개 이상 포함해야 합니다(공백 포함 불가).)");
            System.out.print("비밀번호: ");
            password = sc.nextLine();
            if (!availablePassword(password)) { //availablePassword: 비밀번호의 유효성을 검사하고, 확인 입력을 통해 일치 여부를 확인
                System.out.println("비밀번호 조건을 확인하세요.\n");
                continue;
            }
            System.out.print("비밀번호 확인: ");
            String confirmPassword = sc.nextLine();
            if (password.equals(confirmPassword)) {
                break;
            } else {
                System.out.println("비밀번호를 다시 입력하세요\n");
            }
        }
        save(name, id, password); //입력받은 사용자 정보를 save 메소드를 통해 파일에 저장
        
        System.out.println(name + "님 가입을 축하합니다\n");
        

    }
    private static boolean availablePassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasUpperCase = false; //대문자
        boolean hasLowerCase = false; //소문자
        boolean hasDigit = false; //숫자
        boolean hasSpace = false; //공백
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) { //Character 클래스의 isUpperCase 메소드를 호출
                hasUpperCase = true;
//                System.out.println("upper");
            }
            if (Character.isLowerCase(c)) {
                hasLowerCase = true;
//                System.out.println("lower");
            }
            if (Character.isDigit(c)) {
                hasDigit = true;
//                System.out.println("digit");
            }
            if((c!=' ')) {
                hasSpace = true;
//                System.out.println("space");
            }
        
        }
        return hasUpperCase && hasLowerCase && hasDigit &&hasSpace;
    }
	
	
	
	
	
	private static boolean availableId(String id) {
        List<User_info> users = load(); //load 메소드를 통해 파일에서 사용자 정보를 읽어옴
        for (User_info user : users) { //향상된 for문과 if문을 통해 리스트에 중복되는 아이디가 있는지 확인
            if (user.Get_Id().equals(id)) {
                return false;
            }
        }
        return true;
    }
	
	private static boolean login() {
        System.out.print("아이디: ");
        String id = sc.nextLine();
        System.out.print("비밀번호: ");
        String password = sc.nextLine();

        List<User_info> users = load();
        boolean loginSuccess = false;
        for (int i = users.size() - 1; i >= 0; i--) {
        	User_info user = users.get(i);
            if (user.Get_Id().equals(id) && user.Get_Pw().equals(password)) { //현재 검사중인 user객체의 아이디와 비밀번호가 맞는 경우
                System.out.println("로그인 성공\n");
                loginSuccess = true;
                main_program.currunt_user=user.Get_name();
                main_program.id=user.Get_Id();
                main_program.pw=user.Get_Pw();
                main_program.balance=user.Get_balance();
                main_program.time=user.get_time();
                break;
            }
        }
        if (!loginSuccess) {
            // 로그인 실패 시 메시지를 한 번만 출력
            System.out.println("로그인 실패. 아이디나 비밀번호를 다시 입력하세요.\n");
        }
        return loginSuccess;
    }
	static void save(User_info user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(user.Get_name() + "," + user.Get_Id() + "," + user.Get_Pw() + "," + Integer.toString(user.Get_balance())  + "," + Integer.toString(user.time));  // 사용자 정보를 쉼표로 구분하여 파일에 쓰기
            writer.newLine();
            System.out.println("사용자 정보를 저장하였습니다.");
        } catch (IOException e) {
            System.out.println("사용자 정보를 저장하는 동안 오류가 발생했습니다.");
        }
    }

	private static void save(String name, String id, String password) {
	    save(name, id, password, "100000", "0");
	}
	private static void save(String name, String id, String password, String balance, String time) {
	    List<User_info> users = load(); // 기존 사용자 목록 불러오기

	    // 중복된 사용자 제거
	    users.removeIf(user -> user.Get_Id().equals(id));

	    // 새로운 사용자 추가
	    users.add(new User_info(name, id, password, Integer.parseInt(balance), Integer.parseInt(time)));

	    // 업데이트된 사용자 목록을 파일에 덮어쓰기
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, false))) {
	        for (User_info user : users) {
	            writer.write(user.Get_name() + "," + user.Get_Id() + "," + user.Get_Pw()+","+ user.Get_balance() + "," + user.get_time());
	            writer.newLine();
	        }
	        System.out.println("사용자 정보를 저장하였습니다.");
	    } catch (IOException e) {
	        System.out.println("사용자 정보를 저장하는 동안 오류가 발생했습니다.");
	    }
	}

    // BufferedReader와 FileReader를 사용하여 파일읽기
    private static List<User_info> load() { //users라는 이름의 List 객체를 선언하고, load 메서드를 호출
        List<User_info> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) { // 파일에서 한 줄씩 읽기(null이 아닐 경우 반복문 계속 실행)
                String[] parts = line.split(",");  // 쉼표로 문자열 분리
                if (parts.length ==3) {
                	users.add(new User_info(parts[0], parts[1], parts[2]));
                }
                else if (parts.length == 5) { // 배열의 길이가 3인지 확인
                    users.add(new User_info(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[4]))); // User 객체를 생성하여 리스트에 추가
                }
            }
        } catch (IOException e) {
            System.out.println("사용자 정보를 불러오는 동안 오류가 발생했습니다.");
        }
        return users;
    }
    static void removeDuplicateUsersById() {
        List<User_info> users = load();
        // ID별로 마지막에 등장한 User만 남기기
        Map<String, User_info> uniqueUsers = new LinkedHashMap<>();
        for (User_info user : users) {
            uniqueUsers.put(user.Get_Id(), user); // 같은 ID면 덮어씀(마지막이 남음)
        }
        // 파일 덮어쓰기
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, false))) {
            // uniqueUsers.values()를 리스트로 변환
            List<User_info> userList = new ArrayList<>(uniqueUsers.values());
            // 리스트를 역순으로 정렬
            Collections.reverse(userList);
            // 역순으로 파일에 저장
            for (User_info user : userList) {
                writer.write(user.Get_name() + "," + user.Get_Id() + "," + user.Get_Pw()+","+ user.Get_balance() + "," + user.get_time());
                writer.newLine();
            }
            System.out.println("중복 아이디 정리 및 역순 저장 완료");
        } catch (IOException e) {
            System.out.println("정리 중 오류 발생");
        }
    }
}
