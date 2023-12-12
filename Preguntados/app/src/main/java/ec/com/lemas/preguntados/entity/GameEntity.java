package ec.com.lemas.preguntados.entity;

import java.util.List;

public class GameEntity {
    private String category;
    private List<QuestionEntity> quizes;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<QuestionEntity> getQuizes() {
        return quizes;
    }

    public void setQuizes(List<QuestionEntity> quizes) {
        this.quizes = quizes;
    }
}
