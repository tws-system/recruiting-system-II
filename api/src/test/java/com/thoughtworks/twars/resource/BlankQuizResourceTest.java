package com.thoughtworks.twars.resource;

import com.thoughtworks.twars.bean.BlankQuiz;
import com.thoughtworks.twars.bean.QuizItem;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class BlankQuizResourceTest extends TestBase {

    String basePath = "blankQuizzes";
    BlankQuiz firstBlankQuiz = mock(BlankQuiz.class);
    BlankQuiz secondBlankQuiz = mock(BlankQuiz.class);


    @Test
    public void should_return_all_blank_quizzes() {
        when(blankQuizMapper.findAll()).thenReturn(Arrays.asList(firstBlankQuiz, secondBlankQuiz));
        when(firstBlankQuiz.getId()).thenReturn(1);
        when(secondBlankQuiz.getId()).thenReturn(4);

        Response response = target(basePath).request().get();
        assertThat(response.getStatus(), is(200));

        List<Map> result = response.readEntity(List.class);
        assertThat((String) result.get(0).get("uri"), is("blankQuizzes/1"));
        assertThat((String) result.get(1).get("uri"), is("blankQuizzes/4"));
    }

    @Test
    public void shoud_return_blank_quizzes_by_section_id() {
        when(blankQuizMapper.findBySectionId(1)).thenReturn(Arrays.asList(firstBlankQuiz, secondBlankQuiz));
        when(firstBlankQuiz.getId()).thenReturn(2);
        when(firstBlankQuiz.getType()).thenReturn("singlePuzzle");
        when(firstBlankQuiz.getCount()).thenReturn(10);
        when(firstBlankQuiz.getEasyCount()).thenReturn(3);
        when(firstBlankQuiz.getNormalCount()).thenReturn(4);
        when(firstBlankQuiz.getHardCount()).thenReturn(3);

        Response response = target(basePath + "/1").request().get();
        assertThat(response.getStatus(), is(200));

        List<Map> blankQuizzes = response.readEntity(List.class);

        assertThat(blankQuizzes.get(0).get("id"), is(2));
        assertThat(blankQuizzes.get(0).get("type"), is("singlePuzzle"));
        assertThat(blankQuizzes.get(0).get("count"), is(10));
        assertThat(blankQuizzes.get(0).get("hardCount"), is(3));
        assertThat(blankQuizzes.get(0).get("normalCount"), is(4));
        assertThat(blankQuizzes.get(0).get("easyCount"), is(3));
    }

    @Test
    public void shoud_return_quiz_item_list() {

        QuizItem easyQuizItem = mock(QuizItem.class);
        QuizItem normalQuizItem = mock(QuizItem.class);
        QuizItem hardQuizItem = mock(QuizItem.class);

        when(blankQuizMapper.findOne(anyInt())).thenReturn(firstBlankQuiz);

        when(firstBlankQuiz.getEasyCount()).thenReturn(1);
        when(firstBlankQuiz.getNormalCount()).thenReturn(1);
        when(firstBlankQuiz.getHardCount()).thenReturn(1);

        when(quizItemMapper.getEasyItems(1)).thenReturn(Arrays.asList(easyQuizItem));
        when(quizItemMapper.getNormalItems(1)).thenReturn(Arrays.asList(normalQuizItem));
        when(quizItemMapper.getHardItems(1)).thenReturn(Arrays.asList(hardQuizItem));

        when(easyQuizItem.getId()).thenReturn(88);
        when(easyQuizItem.getDescriptionZh()).thenReturn("描述 88");
        when(easyQuizItem.getQuestionZh()).thenReturn("Question 88");
        when(easyQuizItem.getChartPath()).thenReturn("ChartPath 88");
        when(easyQuizItem.getInitializedBox()).thenReturn("InitializedBox 88");

        when(normalQuizItem.getId()).thenReturn(99);
        when(hardQuizItem.getId()).thenReturn(77);

        Response response = target(basePath + "/1/items").request().get();

        assertThat(response.getStatus(), is(200));

        Map result = response.readEntity(Map.class);
        List<Map> quizItems = (List) result.get("quizItems");

        assertThat(quizItems.size(), is(3));

        assertThat(quizItems.get(0).get("id"), is(88));
        assertThat(quizItems.get(0).get("description"), is("描述 88"));
        assertThat(quizItems.get(0).get("question"), is("Question 88"));
        assertThat(quizItems.get(0).get("chartPath"), is("ChartPath 88"));
        assertThat(quizItems.get(0).get("initializedBox"), is("InitializedBox 88"));

        assertThat(quizItems.get(1).get("id"), is(99));
        assertThat(quizItems.get(2).get("id"), is(77));
    }

    @Test
    public void should_return_examples_when_request_quiz_item() throws Exception {

        QuizItem firstExampleItems = mock(QuizItem.class);
        QuizItem secondExampleItems = mock(QuizItem.class);

        when(blankQuizMapper.findOne(anyInt())).thenReturn(firstBlankQuiz);
        when(firstExampleItems.getId()).thenReturn(88);
        when(secondExampleItems.getId()).thenReturn(99);
        when(firstExampleItems.getInitializedBox()).thenReturn("InitializedBox 88");
        when(firstExampleItems.getQuestionZh()).thenReturn("Question 88");
        when(firstExampleItems.getChartPath()).thenReturn("ChartPath 88");
        when(firstExampleItems.getDescriptionZh()).thenReturn("Description 88");
        when(firstExampleItems.getAnswer()).thenReturn(3);
        when(quizItemMapper.getExampleItems()).thenReturn(Arrays.asList(firstExampleItems,secondExampleItems));

        Response response = target(basePath + "/1/items").request().get();

        assertThat(response.getStatus(), is(200));

        Map result = response.readEntity(Map.class);
        List<Map> quizItems = (List) result.get("exampleItems");

        assertThat(quizItems.size(), is(2));

        assertThat(quizItems.get(0).get("id"), is(88));
        assertThat(quizItems.get(0).get("description"), is("Description 88"));
        assertThat(quizItems.get(0).get("question"), is("Question 88"));
        assertThat(quizItems.get(0).get("chartPath"), is("ChartPath 88"));
        assertThat(quizItems.get(0).get("initializedBox"), is("InitializedBox 88"));
        assertThat(quizItems.get(0).get("answer"),is(3));

    }
}