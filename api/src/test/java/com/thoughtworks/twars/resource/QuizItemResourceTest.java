package com.thoughtworks.twars.resource;

import com.thoughtworks.twars.bean.QuizItem;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuizItemResourceTest extends TestBase{
  QuizItem firstQuizItem = mock(QuizItem.class);
  QuizItem secondQuizItem = mock(QuizItem.class);

  String basePath = "quizItems";


  @Test
  public void should_return_all_quizItems(){
    when(quizItemMapper.getAllQuizItems()).thenReturn(Arrays.asList(firstQuizItem,secondQuizItem));
    when(firstQuizItem.getId()).thenReturn(1);

    Response response = target(basePath).request().get();
    assertThat(response.getStatus(),is(200));

    List<Map> result = (List<Map>) response.readEntity(List.class);
    assertThat((String) result.get(0).get("uri"),is(basePath+"/1"));
  }

  @Test
  public void should_return_insert_quizItem_uri(){
    when(quizItemMapper.insertQuizItem(firstQuizItem)).thenReturn(1);
    when(firstQuizItem.getId()).thenReturn(10);
//    Response response = target(basePath).request().post(Entity.entity(firstQuizItem,MediaType.APPLICATION_JSON),Response.class);
//
//    assertThat(response.getStatus(),is(200));
//
//    Map result = response.readEntity(Map.class);
//    assertThat((String) result.get("uri"),is(basePath+"10"));

//    Entity<QuizItem> quizItemEntity = Entity.entity(firstQuizItem, MediaType.APPLICATION_JSON_TYPE);
//    QuizItem result = target("book").request(MediaType.APPLICATION_JSON_TYPE).post(quizItemEntity, QuizItem.class);
//    assertThat(result.getId(),is(10));

  }

  @Test
  public void should_return_quizItem_by_id(){
    when(quizItemMapper.getQuizItemById(2)).thenReturn(secondQuizItem);
    when(secondQuizItem.getId()).thenReturn(2);
    when(secondQuizItem.getDescriptionZh()).thenReturn("aaa");
    when(secondQuizItem.getInitializedBox()).thenReturn("zzz");
    when(secondQuizItem.getChartPath()).thenReturn("sss");
    when(secondQuizItem.getQuestionZh()).thenReturn("www");

    Response response = target(basePath+"/2").request().get();
    assertThat(response.getStatus(),is(200));

    Map result = response.readEntity(Map.class);
    assertThat((int)result.get("id"),is(2));
    assertThat((String) result.get("initializedBox"),is("zzz"));
    assertThat((String) result.get("description"),is("aaa"));
    assertThat((String) result.get("chartPath"),is("sss"));
    assertThat((String) result.get("question"),is("www"));

  }

}