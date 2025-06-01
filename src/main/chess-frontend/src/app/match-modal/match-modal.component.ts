import {Component, OnInit} from '@angular/core';
import {MatDialogRef, MatDialogTitle} from "@angular/material/dialog";
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {LeaderboardService} from '../leaderboard/leaderboard.service';
import {ChessMatch} from '../model/ChessMatch';
import {Leaderboard} from '../model/Leaderboard';
import {MatFormField, MatLabel} from '@angular/material/input';
import {MatSelect} from '@angular/material/select';
import {MatOption} from '@angular/material/core';
import {PlayerNameRank} from '../model/PlayerNameRank';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-match-modal',
  imports: [
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatSelect,
    MatOption,
    NgForOf,
    MatDialogTitle
  ],
  templateUrl: './match-modal.component.html',
  styleUrl: './match-modal.component.css'
})
export class MatchModalComponent implements OnInit {
  chessMatchForm!: FormGroup;
  errorMessage: string = '';
  displayError: boolean = false;
  players: PlayerNameRank[] = [];

  constructor(
    private dialogRef: MatDialogRef<MatchModalComponent>,
    private builder: FormBuilder,
    private leaderboardService: LeaderboardService,
  ) {
  }

  ngOnInit() {
    this.leaderboardService.getAllPlayers().subscribe((playerList: PlayerNameRank[]) => {
      this.players = playerList;
    });

    this.chessMatchForm = this.builder.group({
      ebonyPlayerId: [],
      ebonyRank: [],
      ivoryPlayerId: [],
      ivoryRank: [],
      datePlayed: [],
      winner: [],
    });
  }

  saveMatch(match: ChessMatch) {
    console.log(match);
    // set values before passing in -> id and rank are blank
    // validate both IDs are different
    // !!! HANDLE ERRORS !!!

    this.leaderboardService.addMatch(match).subscribe({
      next: (leaderboardList: Leaderboard[]) => {
        this.dialogRef.close(leaderboardList);
      },
      error: (err) => {
        this.setErrorMessage(err);
        this.displayError = true;
      }
    });
  }

  closeModal() {
    this.dialogRef.close();
  }

  // @ts-ignore
  setErrorMessage(err) {
    return this.errorMessage = err.error?.message || 'An unexpected error has occurred. Please try again later.';
  }
}
