import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Leaderboard} from '../model/Leaderboard';
import {PlayerDetails} from "../model/PlayerDetails";
import {PlayerRank} from '../model/PlayerRank';
import {ChessMatch} from '../model/ChessMatch';
import {PlayerNameRank} from '../model/PlayerNameRank';

@Injectable({
  providedIn: 'root'
})
export class LeaderboardService {
  private baseUrl = 'http://localhost:8080/v1'

  private getLeaderboardUrl = `${this.baseUrl}/leaderboard`;
  private getPlayersUrl = `${this.baseUrl}/players`;
  private addPlayerUrl = `${this.baseUrl}/players/newPlayer`;
  private addMatchUrl = `${this.baseUrl}/leaderboard/matches`;
  private updatePlayerDetailUrl = `${this.baseUrl}/players/update`;

  constructor(private http: HttpClient) {
  }

  getLeaderboard(): Observable<Leaderboard[]> {
    return this.http.get<Leaderboard[]>(this.getLeaderboardUrl);
  }

  getPlayerById(playerId: string): Observable<PlayerRank> {
    return this.http.get<PlayerRank>(`${this.baseUrl}/players/byId/${playerId}`);
  }

  getAllPlayers(): Observable<PlayerNameRank[]> {
    return this.http.get<PlayerNameRank[]>(this.getPlayersUrl);
  }

  addNewPlayer(playerDetails: PlayerDetails): Observable<Leaderboard[]> {
    return this.http.post<Leaderboard[]>(this.addPlayerUrl, playerDetails);
  }

  addMatch(chessMatch: ChessMatch): Observable<Leaderboard[]> {
    return this.http.post<Leaderboard[]>(this.addMatchUrl, chessMatch);
  }

  updatePlayer(playerDetails: PlayerDetails): Observable<Leaderboard[]> {
    return this.http.put<Leaderboard[]>(this.updatePlayerDetailUrl, playerDetails);
  }

  removePlayer(playerId: string): Observable<Leaderboard[]> {
    return this.http.delete<Leaderboard[]>(`${this.baseUrl}/players/removePlayer/${playerId}`);
  }
}
