# Yêu cầu thực hiện các method HTTP

- Bước 1: Tạo chương trình SpringBoot với Spring Web & Spring Boot DevTools
- Bước 2: Thêm các thư mục và File trong src/main/java/vn/techmaster/bookstore như nhìn :
![1](img/1.png)
- Bước 3: Tạo Constructors,Get&Set cho file Book.java vs các id ,title,description
- Bước 4: Tạo cái Category theo yêu cầu bài tập ở file Dao.java
  public abstract class Dao<T> {
  protected  List<T> collections = new ArrayList<>();

  public abstract List<T> getAll();

  public abstract Optional<T> get(int id);
 
  public abstract void add(T t);

  public abstract void update(T t);

  public abstract void deleteByID(int id);
  }
- Bước 5: Giải các yêu cầu bài tập ở file BookDao.java
![2](img/2.png)
- Bước 6: Thực hiện các lệnh Get, Put, Post, Delete ở file RESTController.java
![3](img/2.png)
- Bước 7: Test kết quả ở PostMan với các lệnh Get, Put, Post, Delete