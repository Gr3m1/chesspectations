import {Component, OnInit} from '@angular/core';
import {Leaderboard} from '../model/Leaderboard';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable,
  MatTableDataSource
} from '@angular/material/table';
import {LeaderboardService} from './leaderboard.service';
import {MatIconButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {PlayerDetails} from '../model/PlayerDetails';
import {ChessMatch} from '../model/ChessMatch';

@Component({
  selector: 'app-leaderboard',
  imports: [
    MatHeaderCell,
    MatCell,
    MatTable,
    MatHeaderRow,
    MatRow,
    MatHeaderCellDef,
    MatCellDef,
    MatHeaderRowDef,
    MatRowDef,
    MatColumnDef,
    MatIconButton,
    MatIcon,
  ],
  templateUrl: './leaderboard.component.html',
  styleUrl: './leaderboard.component.css'
})
export class LeaderboardComponent implements OnInit {
  displayedColumns: string[] = ['ranking', 'playerName', 'gamesPlayed', 'actions'];
  dataSource = new MatTableDataSource<Leaderboard>();
  errorMessage: string = '';
  displayError: boolean = false;
  success = false;

  constructor(private leaderboardService: LeaderboardService) {
  }

  ngOnInit(): void {
    this.getLeaderboard();
  }

  getLeaderboard() {
    this.leaderboardService.getLeaderboard().subscribe({
      next: (leaderboardArr) => this.dataSource.data = leaderboardArr,
      error: (err) => {
        this.setErrorMessage(err);
        this.displayError = true;
      }
    })
  }

  openNewPlayerModal() {

  }

  openMatchModal() {

  }

  addNewPlayer(newPlayer: PlayerDetails) {
    this.leaderboardService.addNewPlayer(newPlayer).subscribe({
      next: leaderboardArr => {
        this.dataSource.data = leaderboardArr
        this.success = true;
        // this.modal.close();
        // if success, show large yes button for 3 seconds, then close modal and reload dashboard

        console.log(leaderboardArr)
      },
      error: (err) => {
        this.setErrorMessage(err);
        this.displayError = true;
        this.success = false;
      }
    })
  }

  addMatch(chessMatch: ChessMatch) {
    this.leaderboardService.addMatch(chessMatch).subscribe({
      next: value => {
        this.success = true;
        // if success, show large yes button for 3 seconds, then close modal and reload dashboard
        this.getLeaderboard();
        console.log(value)
      },
      error: (err) => {
        this.setErrorMessage(err);
        this.displayError = true;
        this.success = false;
      }
    })
  }

  viewPlayer(id: string) {
    this.leaderboardService.getPlayerById(id).subscribe({
      next: value => {
        this.success = true;
        // if success, show large yes button for 3 seconds, open new modal showing player details
        console.log(value)
      },
      error: (err) => {
        this.setErrorMessage(err);
        this.displayError = true;
        this.success = false;
      }
    })
  }

  editPlayer(updatedPlayer: PlayerDetails) {
    this.leaderboardService.updatedPlayer(updatedPlayer).subscribe({
      next: player => {
        this.success = true;
        // populate player modal with new values
        console.log(player)
      },
      error: (err) => {
        this.setErrorMessage(err);
        this.displayError = true;
        this.success = false;
      }
    })
  }

  removePlayer(id: string) {
    this.leaderboardService.removePlayer(id).subscribe({
      next: value => {
        this.success = true;
        // if success, show large yes button for 3 seconds, open new modal showing player details
        console.log(value)
      },
      error: (err) => {
        this.setErrorMessage(err);
        this.displayError = true;
        this.success = false;
      }
    })
  }

  // @ts-ignore
  setErrorMessage(err) {
    return this.errorMessage = err.error?.message || 'An unexpected error has occurred. Please try again later.';
  }
}
