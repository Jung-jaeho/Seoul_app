package uis.Manage;

public class AppQuestion {
    /* QuestionFragment 내용 저장 */

    private int langCode;

    private String[] question = new String[4];
    private String[] qusetion1 = {"여기에 어떻게 가나요?", "How do I get here?", "ここでどのように行くか？", "我怎么到这里来？"};
    private String[] question2 = {"화장실은 어디에 있나요?", "Where is the restroom?", "トイレはどこにありますか？", "浴室在哪里？"};
    private String[] question3 = {"매표소는 어디에 있나요?", "Where is the ticket office?", "切符売り場はどこにありますか？",
            "售票处在哪里？"};
    private String[] question4 = {"요금은 얼마 인가요?", "How much is the fare?", "料金はいくらですか？",
            "票价多少钱？"};

    public AppQuestion(int langCode) {
        this.langCode = langCode;
        switch (langCode){
            case 0: // Korean
                question[0] = qusetion1[0];
                question[1] = question2[0];
                question[2] = question3[0];
                question[3] = question4[0];
                break;
            case 1: // English
                question[0] = qusetion1[1];
                question[1] = question2[1];
                question[2] = question3[1];
                question[3] = question4[1];
                break;
            case 2: // Japanese
                question[0] = qusetion1[2];
                question[1] = question2[2];
                question[2] = question3[2];
                question[3] = question4[2];
                break;
            case 3: // Chinese
                question[0] = qusetion1[3];
                question[1] = question2[3];
                question[2] = question3[3];
                question[3] = question4[3];
                break;
        }
    }

    public int getLangCode() {
        return langCode;
    }

    public String[] getQuestion() {
        return question;
    }

    public String[] getQusetion1() {
        return qusetion1;
    }

    public String[] getQuestion2() {
        return question2;
    }

    public String[] getQuestion3() {
        return question3;
    }

    public String[] getQuestion4() {
        return question4;
    }
}
