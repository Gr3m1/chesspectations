import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Leaderboard} from '../model/Leaderboard';
import {PlayerDetails} from "../model/PlayerDetails";
import {PlayerRank} from '../model/PlayerRank';
import {ChessMatch} from '../model/ChessMatch';
import {PlayerNameRank} from '../model/PlayerNameRank';
import {AuthService} from './auth.service';
import {ChessMatchWithNames} from '../model/ChessMatchWithNames';

@Injectable({
  providedIn: 'root'
})
export class LeaderboardService {
  username = 'frontend';
  password = 'password';

  private baseUrl = 'http://localhost:8080/v1'

  private getLeaderboardUrl = `${this.baseUrl}/leaderboard`;
  private playersUrl = `${this.baseUrl}/players`;
  private addPlayerUrl = `${this.baseUrl}/players/newPlayer`;
  private matchUrl = `${this.baseUrl}/chessMatch`;
  private updatePlayerDetailUrl = `${this.baseUrl}/players/update`;

  constructor(
    private http: HttpClient,
    private authService: AuthService) {
  }

  // no auth
  getLeaderboard(): Observable<Leaderboard[]> {
    return this.http.get<Leaderboard[]>(this.getLeaderboardUrl);
  }

  // no auth
  getPastMatches(playerId: string): Observable<ChessMatchWithNames[]> {
    console.log("getPastMatches")
    return this.http.get<ChessMatchWithNames[]>(`${this.matchUrl}/history/${playerId}`);
  }

  // admin only - go through auth service
  getPlayerById(playerId: string): Observable<PlayerRank> {
    return this.authService.get<PlayerRank>(`${this.playersUrl}/byId/${playerId}`);
  }

  // admin only - go through auth service
  getAllPlayers(): Observable<PlayerNameRank[]> {
    return this.authService.get<PlayerNameRank[]>(`${this.playersUrl}`);
  }

  // admin only - go through auth service
  addNewPlayer(playerDetails: PlayerDetails): Observable<Leaderboard[]> {
    return this.authService.post<Leaderboard[]>(this.addPlayerUrl, playerDetails);
  }

  // admin only - go through auth service
  addMatch(chessMatch: ChessMatch): Observable<Leaderboard[]> {
    return this.authService.post<Leaderboard[]>(`${this.matchUrl}/addMatch`, chessMatch);
  }

  // admin only - go through auth service
  updatePlayer(playerDetails: PlayerDetails): Observable<Leaderboard[]> {
    return this.authService.put<Leaderboard[]>(this.updatePlayerDetailUrl, playerDetails);
  }

  // admin only - go through auth service
  removePlayer(playerId: string): Observable<Leaderboard[]> {
    return this.authService.delete<Leaderboard[]>(`${this.playersUrl}/removePlayer/${playerId}`);
  }
}
