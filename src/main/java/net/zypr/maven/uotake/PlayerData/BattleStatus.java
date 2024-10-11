package net.zypr.maven.uotake.PlayerData;

public class BattleStatus {
    private int wins;
    private int losses;
    private int draws;
    private int kills;
    private int deaths;
    private int bonusPoints;

    public BattleStatus(int wins, int losses, int draws, int kills, int deaths, int bonusPoints) {
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
        this.kills = kills;
        this.deaths = deaths;
        this.bonusPoints = bonusPoints;
    }

    // ゲッターとセッター
    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(int bonusPoints) {
        this.bonusPoints = bonusPoints;
    }
}


