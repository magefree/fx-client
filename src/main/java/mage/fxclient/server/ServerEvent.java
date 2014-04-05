package mage.fxclient.server;

/**
 *
 * @author North
 */
public enum ServerEvent {

    startGame,
    startTournament,
    startDraft,
    replayGame,
    showTournament,
    watchGame,
    chatMessage,
    serverMessage,
    joinedTable,
    replayInit,
    replayDone,
    replayUpdate,
    gameInit,
    gameOver,
    gameError,
    gameAsk,
    gameTarget,
    gameSelect,
    gameChooseAbility,
    gameChoosePile,
    gameChoose,
    gamePlayMana,
    gamePlayXMana,
    gameSelectAmount,
    gameUpdate,
    endGameInfo,
    showUserMessage,
    gameInform,
    gameInformPersonal,
    sideboard,
    construct,
    draftOver,
    draftPick,
    draftUpdate,
    draftInform,
    draftInit,
    tournamentInit;

    public static ServerEvent fromMethod(String method) {
        return ServerEvent.valueOf(method);
    }
}
