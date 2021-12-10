package vn.techmaster.imdb.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import vn.techmaster.imdb.model.Film;

@Repository
public class FilmRepository implements IFilmRepo {
  private List<Film> films;

  public FilmRepository(@Value("${datafile}") String datafile) {
    try {
      File file = ResourceUtils.getFile("classpath:static/" + datafile);
      ObjectMapper mapper = new ObjectMapper(); // Dùng để ánh xạ cột trong CSV với từng trường trong POJO
      films = Arrays.asList(mapper.readValue(file, Film[].class));
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public List<Film> getAll() {
    return films;
  }

  // Phân loại danh sách film theo quốc gia sản xuất
  @Override
  public Map<String, List<Film>> getFilmByCountry() {
    Map<String, List<Film>> myMap = new HashMap<String, List<Film>>();
    for (Film film : films) {
      myMap.put(film.getCountry(), new ArrayList<>());
    }
    Set<String> mySet = myMap.keySet();
    for (Film film : films) {
      if (mySet.contains(film.getCountry())) {
        myMap.get(film.getCountry()).add(film);
      }
    }
    return myMap;
  }

  // Nước nào sản xuất nhiều film nhất, số lượng bao nhiêu?
  @Override
  public Entry<String, Integer> getcountryMakeMostFilms() {
    Map<String, Integer> mostFilms = new HashMap<>();
    int count = 0;
    for (Film film : films) {
      if (!mostFilms.containsKey(film.getCountry())) {
        mostFilms.put(film.getCountry(), 1);
      } else {
        int soLanLap = mostFilms.get(film.getCountry());
        soLanLap++;
        mostFilms.put(film.getCountry(), soLanLap);
      }
    }
    for (Map.Entry<String, Integer> most : mostFilms.entrySet()) {
      if (most.getValue() > count) {
        count = most.getValue();
      }
    }
    for (Map.Entry<String, Integer> countryMakeMostFilm : mostFilms.entrySet()) {
      if (countryMakeMostFilm.getValue() == count) {
        return countryMakeMostFilm;
      }
    }
    return null;
  }

  // Năm nào sản xuất nhiều film nhất, số lượng bao nhiêu?
  @Override
  public Entry<Integer, Integer> yearMakeMostFilms() {
    Map<Integer, Integer> mostYear = new HashMap<>();
    int count = 0;
    for (Film film : films) {
      if (!mostYear.containsKey(film.getYear())) {
        mostYear.put(film.getYear(), 1);
      } else {
        int soLanLap = mostYear.get(film.getYear());
        soLanLap++;
        mostYear.put(film.getYear(), soLanLap);
      }
    }
    for (Map.Entry<Integer, Integer> most : mostYear.entrySet()) {
      if (most.getValue() > count) {
        count = most.getValue();
      }
    }
    for (Map.Entry<Integer, Integer> yearMakeMostFilm : mostYear.entrySet()) {
      if (yearMakeMostFilm.getValue() == count) {
        return yearMakeMostFilm;
      }
    }
    return null;
  }

  // Danh sách tất cả thể loại film
  @Override
  public List<String> getAllGeneres() {
    List<String> allGeneres = new ArrayList<>();
    for (Film film : films) {
      for (int i = 0; i < film.getGeneres().size(); i++) {
        if (!allGeneres.contains(film.getGeneres().get(i)) && film.getGeneres().get(i) != "") {
          allGeneres.add(film.getGeneres().get(i));
        }
      }
    }
    return allGeneres;
  }

  // Tìm film do một quốc gia sản xuất từ năm X đến năm Y
  @Override
  public List<Film> getFilmsMadeByCountryFromYearToYear(String country, int fromYear, int toYear) {
    List<Film> myfilm = new ArrayList<>();
    for (Film film : films) {
      if (film.getCountry().toLowerCase().equals(country.toLowerCase()) && film.getYear() >= fromYear
          && film.getYear() <= toYear) {
        myfilm.add(film);
      }
    }
    return myfilm;
  }

  // Phân loại film theo thể loại
  @Override
  public Map<String, List<Film>> categorizeFilmByGenere() {
    Map<String, List<Film>> category = new HashMap<>();
    List<String> allGeneres = new ArrayList<>();
    for (Film film : films) {
      for (int i = 0; i < film.getGeneres().size(); i++) {
        if (!category.keySet().contains(film.getGeneres().get(i)) && film.getGeneres().get(i) != "") {
          allGeneres.add(film.getGeneres().get(i));
          category.put(film.getGeneres().get(i), new ArrayList<>());
        }
      }
    }

    for (int i = 0; i < allGeneres.size(); i++) {
      for (Film film : films) {
        if (film.getGeneres().contains(allGeneres.get(i))) {
          category.get(allGeneres.get(i)).add(film);
        }
      }
    }

    return category;
  }

  // Top 5 film có lãi lớn nhất margin = revenue - cost
  @Override
  public List<Film> top5HighMarginFilms() {
    List<Integer> marginFilm = new ArrayList<>();
    List<Film> marginTop5 = new ArrayList<>();
    for (Film film : films) {
      marginFilm.add(film.getRevenue()-film.getCost());
    }
    marginFilm.sort((o1, o2) -> o2 - o1);
    for (int i = 0; i < 5; i++) {
      for (Film film : films) {
        if(film.getRevenue()-film.getCost() == marginFilm.get(i)){
          marginTop5.add(film);
        }
      }
    }
    while(marginTop5.size()>5){
      marginTop5.remove(marginTop5.size()-1);
    }
    return marginTop5;
  }

   // Top 5 film từ năm 1990 đến 2000 có lãi lớn nhất
  @Override
  public List<Film> top5HighMarginFilmsIn1990to2000() {
    List<Integer> marginFilm = new ArrayList<>();
    List<Film> marginTop5 = new ArrayList<>();
    for (Film film : films) {
      if(film.getYear()>=1990 && film.getYear()<=2000){
      marginFilm.add(film.getRevenue()-film.getCost());
      }
    }
    marginFilm.sort((o1, o2) -> o2 - o1);
    for (int i = 0; i < 5; i++) {
      for (Film film : films) {
        if(film.getRevenue()-film.getCost() == marginFilm.get(i)){
          marginTop5.add(film);
        }
      }
    }
    while(marginTop5.size()>5){
      marginTop5.remove(marginTop5.size()-1);
    }
    return marginTop5;
  }


  //Tỷ lệ phim giữa 2 thể loại
  @Override
  public double ratioBetweenGenere(String genreX, String genreY) {
    Map<String, Integer> category = new HashMap<>();
    List<String> allGeneres = new ArrayList<>();
    for (Film film : films) {
      for (int i = 0; i < film.getGeneres().size(); i++) {
        if (!category.keySet().contains(film.getGeneres().get(i)) && film.getGeneres().get(i) != "") {
          allGeneres.add(film.getGeneres().get(i));
          category.put(film.getGeneres().get(i),0);
        }
      }
    }

    for (int i = 0; i < allGeneres.size(); i++) {
      for (Film film : films) {
        if (film.getGeneres().contains(allGeneres.get(i))) {
          int soLanLap = category.get(allGeneres.get(i));
          soLanLap++;
          category.put(allGeneres.get(i), soLanLap);
        }
      }
    }
    double x = (double) category.get(genreX);
    double y = (double) category.get(genreY);
    return x/y;
  }

  //Top 5 film có rating cao nhất nhưng lãi thì thấp nhất (thậm chí lỗ)
  @Override
  public List<Film> top5FilmsHighRatingButLowMargin() {
    List<Film> top5 = new ArrayList<>();
    List<Integer> rating = new ArrayList<>();
    List<Integer> top5Rating = new ArrayList<>();

    for (Film film : films) {
      rating.add((int)film.getRating());
    }
    rating.sort((o1, o2) -> o2 - o1);
    for (int i = 0; i < 5; i++) {
      top5Rating.add(rating.get(i));
      }
    for (Film film : films) {
      if(top5Rating.contains((int)film.getRating())){
        top5.add(film);
      }
    }
    if(top5.size() > 5){
      List<Integer> margin = new ArrayList<>();
      for (int i = 0; i < top5.size(); i++) {
        margin.add(top5.get(i).getRevenue()-top5.get(i).getCost());
      }
      margin.sort((o1, o2) -> o1 - o2);   
      while(margin.size()>5){
      margin.remove(margin.size()-1);
      }
      for (int i = 0; i < top5.size(); i++) {
      if(!margin.contains(top5.get(i).getRevenue()-top5.get(i).getCost())){
        top5.remove(top5.get(i));
      }
      }
   
    }
    return top5;
  }
}
