package uis.Manage;

public class AppNotice {

    /* AlertDialog나 Toast 내용 저장 */
    private int langCode;

    // MainActivity
    private String exit;

    // Favorite Fragment
    private String addFavorite, deleteFavorite;

    // Setting Fragment
    private String changeLang;

    // search Dialog
    private String search;

    // SttFragment
    private String notice;
    private String noService, lte, none;
    private String delete, deleteNotice;
    private String yes, no, fine;

    public AppNotice(int langCode) {
        this.langCode = langCode;
        switch (langCode){
            case 0: // Korean
                exit = "앱을 종료하려면 '뒤로' 버튼을 한번 더 눌러주세요.";
                addFavorite = "즐겨찾기 추가"; deleteFavorite = "즐겨찾기 취소";
                changeLang = "언어 설정이 변경되었습니다.";
                search = "검색어를 입력해주세요.";
                notice = "공지";
                noService = "죄송합니다.\n이 번역은 현재 사용할 수 없습니다." +
                        "해당 서비스는 추후에 업데이트 될 예정입니다.";
                lte = "이 서비스에는 많은 양의 데이터가 필요할 수 있습니다. " +
                        "WIFI 환경에서 이 서비스를 사용하는 것이 좋습니다.";
                none = "이 서비스에는 인터넷이 필요합니다. 인터넷에 연결하고 다시 시도하십시오.";
                delete = "삭제";
                deleteNotice = "이 메시지를 삭제하시겠습니까?";
                yes = "네"; no = "아니오";  fine = "괜찮아요";

                break;
            case 1: // English
                exit = "To exit the app, press the 'Back' button again.";
                addFavorite = "Add to Favorites"; deleteFavorite = "Cancel favorites";
                changeLang = "The language setting has been changed.";
                search = "Please enter your search term.";
                notice = "NOTICE";
                noService = "Sorry.\nThis translation is not currenty available. " +
                        "The service will be updated in the future.";
                lte = "This service may require a large amount of data. " +
                        "We recommend using the service in the WIFI environment.";
                none = "This service requires the Internet. Please connect to the Internet and try again.";
                delete = "DELETE";
                deleteNotice = "Do you want to delete this message?";
                yes = "Yes"; no = "No";  fine = "It's OK";
                break;
            case 2: // Japanese
                exit = "アプリを終了するには、「戻る」ボタンをもう一度押してください。";
                addFavorite = "気に入りに追加"; deleteFavorite = "お気に入り解除";
                changeLang = "言語の設定が変更されました。";
                search = "検索用語を入力してください。";
                notice = "通知";
                noService = "ごめんなさい\nの翻訳は現在利用できません。このサービスは将来更新されます。";
                lte = "このサービスには大量のデータが必要な場合があります。 " +
                        " WIFI環境でこのサービスを使用することをお勧めします。";
                none = "このサービスにはインターネットが必要です。インターネットに接続して、もう一度お試しください。";
                delete = "削除";
                deleteNotice = "このメッセージを削除しますか？";
                yes = "はい"; no = "いいえ"; fine = "いいんだよ";
                break;
            case 3: // Chinese
                exit = "要退出应用程序，请再次按 '返回' 按钮。";
                addFavorite = "添加到收藏夹"; deleteFavorite = "取消收藏";
                changeLang = "语言设置已更改。";
                search = "请输入您的搜索字词.";
                notice = "注意";
                noService = "抱歉\n此翻译目前无法使用。该服务将在未来更新。.";
                lte = "此服务可能需要大量数据。 我们建议在WIFI环境中使用该服务。";
                none = "此服务需要Internet。请连接到互联网，然后重试。";
                delete = "删除";
                deleteNotice = "要删除此信息吗？";
                yes = "是"; no = "没有"; fine = "没关系";
                break;
        }
    }

    public int getLangCode() {
        return langCode;
    }

    public String getExit() {
        return exit;
    }

    public String getAddFavorite() {
        return addFavorite;
    }

    public String getDeleteFavorite() {
        return deleteFavorite;
    }

    public String getChangeLang() {
        return changeLang;
    }

    public String getSearch() {
        return search;
    }

    public String getNotice() {
        return notice;
    }

    public String getNoService() {
        return noService;
    }

    public String getLte() {
        return lte;
    }

    public String getNone() {
        return none;
    }

    public String getDelete() {
        return delete;
    }

    public String getDeleteNotice() {
        return deleteNotice;
    }

    public String getYes() {
        return yes;
    }

    public String getNo() {
        return no;
    }

    public String getFine() {
        return fine;
    }
}
