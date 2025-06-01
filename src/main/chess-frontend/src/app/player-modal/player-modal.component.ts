import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef, MatDialogTitle} from '@angular/material/dialog';
import {PlayerRank} from '../model/PlayerRank';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {LeaderboardService} from '../leaderboard/leaderboard.service';
import {Leaderboard} from '../model/Leaderboard';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-player-modal',
  imports: [
    ReactiveFormsModule,
    MatDialogTitle,
    NgIf
  ],
  templateUrl: './player-modal.component.html',
  styleUrl: './player-modal.component.css'
})
export class PlayerModalComponent implements OnInit {
  playerForm!: FormGroup;
  errorMessage: string = '';
  displayError: boolean = false;
  isEditable: boolean = false;
  existingPlayer: boolean = false;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: {
      player: PlayerRank,
      shouldEdit: boolean
    },
    private dialogRef: MatDialogRef<PlayerModalComponent>,
    private builder: FormBuilder,
    private leaderboardService: LeaderboardService,
  ) {
  }

  ngOnInit() {
    this.isEditable = this.data.shouldEdit;

    this.playerForm = this.builder.group({
      ranking: [{value: this.data.player.ranking, disabled: true}],
      id: [{value: this.data.player.id, disabled: true}],
      playerName: [{value: this.data.player.playerName, disabled: !this.data.shouldEdit}],
      emailAddress: [{value: this.data.player.emailAddress, disabled: !this.data.shouldEdit}],
      dateOfBirth: [{value: this.data.player.dateOfBirth, disabled: !this.data.shouldEdit}],
      gamesPlayed: [{value: this.data.player.gamesPlayed, disabled: true}],
    })
  }

  editPlayer() {
    this.isEditable = true;
    this.playerForm.enable();
    this.existingPlayer = true;
  }

  savePlayer(newPlayer: PlayerRank) {
    if (this.existingPlayer) {
      this.leaderboardService.updatePlayer(newPlayer).subscribe({
        next: (leaderboardList: Leaderboard[]) => {
          this.dialogRef.close(leaderboardList);
        },
        error: (err) => {
          this.setErrorMessage(err);
          this.displayError = true;
        }
      })
    } else {
      this.leaderboardService.addNewPlayer(newPlayer).subscribe({
        next: (leaderboardList: Leaderboard[]) => {
          this.dialogRef.close(leaderboardList);
        },
        error: (err) => {
          this.setErrorMessage(err);
          this.displayError = true;
        }
      })
    }

    this.leaderboardService.updatePlayer(newPlayer).subscribe({
      next: (leaderboardList: Leaderboard[]) => {
        this.dialogRef.close(leaderboardList);
      },
      error: (err) => {
        this.setErrorMessage(err);
        this.displayError = true;
      }
    });
  }

  removePlayer(player: PlayerRank) {
    this.leaderboardService.removePlayer(player.id).subscribe({
      next: (leaderboardList: Leaderboard[]) => {
        this.dialogRef.close(leaderboardList);
      },
      error: (err) => {
        this.setErrorMessage(err);
        this.displayError = true;
      }
    })
  }

  closeModal() {
    this.dialogRef.close();
  }

  // @ts-ignore
  setErrorMessage(err) {
    return this.errorMessage = err.error?.message || 'An unexpected error has occurred. Please try again later.';
  }
}
