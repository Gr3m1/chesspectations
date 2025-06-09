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
import {LeaderboardService} from '../services/leaderboard.service';
import {MatIconButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {PlayerModalComponent} from '../player-modal/player-modal.component';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {PlayerRank} from '../model/PlayerRank';
import {MatchModalComponent} from '../match-modal/match-modal.component';
import {LoginComponent} from '../login/login.component';
import {NgIf} from '@angular/common';
import {AuthService} from '../services/auth.service';

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
    NgIf,
  ],
  templateUrl: './leaderboard.component.html',
  styleUrl: './leaderboard.component.css'
})
export class LeaderboardComponent implements OnInit {
  displayedColumns: string[] = ['ranking', 'playerName', 'gamesPlayed'];
  dataSource = new MatTableDataSource<Leaderboard>();
  errorMessage: string = '';
  displayError: boolean = false;
  isLoading = true;
  isLoggedIn = false;
  username: string = '';

  constructor(private leaderboardService: LeaderboardService,
              private authService: AuthService,
              private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.getLeaderboard();
  }

  getLeaderboard() {
    this.leaderboardService.getLeaderboard().subscribe({
      next: (leaderboardArr) => {
        this.dataSource.data = leaderboardArr;
        if (this.authService.isLoggedIn()) {
          if (!this.displayedColumns.indexOf('actions')) {
            this.displayedColumns.push('actions');
          }
          this.isLoggedIn = true;
        }
        this.isLoading = false;
      },
      error: (err) => {
        this.setErrorMessage(err);
        this.displayError = true;
      }
    })
  }

  openPlayerModal(playerView: PlayerRank, isNewPlayer: boolean): MatDialogRef<PlayerModalComponent> {
    return this.dialog.open(PlayerModalComponent, {
      width: '40vw',
      height: '50vh',
      maxWidth: '100vw',
      panelClass: 'custom-modal-class',
      data: {
        player: playerView,
        shouldEdit: isNewPlayer,
      }
    });
  }

  viewPlayer(player: Leaderboard) {
    this.leaderboardService.getPlayerById(player.playerId).subscribe({
      next: playerView => {
        const dialogRef = this.openPlayerModal(playerView, false);
        dialogRef.afterClosed().subscribe((returnedData: Leaderboard[] | undefined) => {
          if (returnedData && returnedData.length) {
            this.dataSource.data = returnedData;
          } else {
            this.getLeaderboard();
          }
        })
        this.isLoading = false;
      },
      error: (err) => {
        this.setErrorMessage(err);
        this.displayError = true;
      }
    });
  }

  addNewPlayer() {
    const emptyPlayer: PlayerRank = {
      id: '',
      ranking: 0,
      playerName: '',
      emailAddress: '',
      dateOfBirth: new Date(0),
      gamesPlayed: 0
    };
    const dialogRef = this.openPlayerModal(emptyPlayer, true);
    dialogRef.afterClosed().subscribe((returnedData: Leaderboard[] | undefined) => {
      if (returnedData && returnedData.length) {
        this.dataSource.data = returnedData;
      } else {
        this.getLeaderboard();
      }
    })
    this.isLoading = false;
  }

  openMatchModal(): MatDialogRef<MatchModalComponent> {
      return this.dialog.open(MatchModalComponent, {
        width: '40vw',
        height: '40vh',
        maxWidth: '100vw',
        panelClass: 'custom-modal-class',
      });
  }

  addMatch() {
    const dialogRef = this.openMatchModal();
    dialogRef.afterClosed().subscribe((returnedData: Leaderboard[] | undefined) => {
      if (returnedData && returnedData.length) {
        this.dataSource.data = returnedData;
      } else {
        this.getLeaderboard();
      }
    })
    this.isLoading = false;
  }

  openLoginModal(): MatDialogRef<LoginComponent> {
    return this.dialog.open(LoginComponent, {
      width: '40vw',
      height: '40vh',
      maxWidth: '100vw',
      panelClass: 'custom-modal-class',
    });
  }

  login() {
    const dialogRef = this.openLoginModal();
    dialogRef.afterClosed().subscribe(result => {
      if (result?.validationResult) {
        this.isLoggedIn = true;
        this.username = result?.username;
        this.displayedColumns.push('actions');
      } else {
        console.log("Login failed")
        this.isLoggedIn = false;
      }
    });
    this.isLoggedIn = false;
  }

  logout() {
    this.authService.logout();
    this.isLoggedIn = false;
    this.username = '';
  }

  // @ts-ignore
  setErrorMessage(err) {
    return this.errorMessage = err.error?.message || 'An unexpected error has occurred. Please try again later.';
  }
}
