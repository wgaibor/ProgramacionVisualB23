package ec.com.lemas.preguntados.entity;

import java.util.List;

public class QuestionEntity {
    private String question;
    private List<ChoiceEntity> choices;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<ChoiceEntity> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceEntity> choices) {
        this.choices = choices;
    }
}
