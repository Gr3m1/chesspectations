import {Component, OnInit} from '@angular/core';
import {MatDialogRef, MatDialogTitle} from "@angular/material/dialog";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {LeaderboardService} from '../services/leaderboard.service';
import {PlayerNameRank} from '../model/PlayerNameRank';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectChange, MatSelectModule} from '@angular/material/select';
import {MatOption} from '@angular/material/core';
import {NgForOf} from '@angular/common';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatInputModule} from '@angular/material/input';
import {Leaderboard} from '../model/Leaderboard';

@Component({
  selector: 'app-match-modal',
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatOption,
    NgForOf,
    MatDialogTitle,
    MatDatepickerModule,
    MatInputModule,
  ],
  templateUrl: './add-match-modal.component.html',
  styleUrl: './add-match-modal.component.css'
})
export class AddMatchModalComponent implements OnInit {
  chessMatchForm!: FormGroup;
  errorMessage: string = '';
  displayError: boolean = false;
  players: PlayerNameRank[] = [];

  constructor(
    private dialogRef: MatDialogRef<AddMatchModalComponent>,
    private formBuilder: FormBuilder,
    private leaderboardService: LeaderboardService,
  ) {
  }

  ngOnInit() {
    this.leaderboardService.getAllPlayers().subscribe((playerList: PlayerNameRank[]) => {
      this.players = playerList;
    });

    this.chessMatchForm = this.formBuilder.group({
      ebonyPlayerId: [null, Validators.required],
      ebonyRank: [null],
      ivoryPlayerId: [null, Validators.required],
      ivoryRank: [null],
      datePlayed: [null, Validators.required],
      winner: [null, Validators.required],
    });
  }

  onEbonyPlayerChange(event: MatSelectChange): void {
    const playerId = event.value;
    const selectedPlayer = this.players.find(p => p.playerId === playerId);

    if (selectedPlayer) {
      this.chessMatchForm.patchValue({
        ebonyRank: selectedPlayer.ranking
      });
    }
  }

  onIvoryPlayerChange(event: MatSelectChange): void {
    const playerId = event.value;
    const selectedPlayer = this.players.find(p => p.playerId === playerId);

    if (selectedPlayer) {
      this.chessMatchForm.patchValue({
        ivoryRank: selectedPlayer.ranking
      });
    }
  }

  saveMatch() {
    if (this.chessMatchForm.valid) {
      const rawDate: Date = this.chessMatchForm.value.datePlayed;
      const formattedDate: string = rawDate.toISOString().split('T')[0]

      const request = {
        ebonyPlayerId: this.chessMatchForm.value.ebonyPlayerId,
        ebonyRank: this.chessMatchForm.value.ebonyRank,
        ivoryPlayerId: this.chessMatchForm.value.ivoryPlayerId,
        ivoryRank: this.chessMatchForm.value.ivoryRank,
        datePlayed: formattedDate,
        winner: this.chessMatchForm.value.winner,
      };

      this.leaderboardService.addMatch(request).subscribe({
        next: (leaderboardList: Leaderboard[]) => {
          this.dialogRef.close(leaderboardList);
        },
        error: (err) => {
          this.setErrorMessage(err);
          this.displayError = true;
        }
      });
    } else {
      console.warn("Invalid form: ", this.chessMatchForm.value);
      this.chessMatchForm.markAllAsTouched();
    }
  }

  isSaveButtonDisabled(): boolean {
    const form = this.chessMatchForm;
    const ebonyId = form.get('ebonyPlayerId')?.value;
    const ivoryId = form.get('ivoryPlayerId')?.value;

    return form.invalid || (ebonyId && ivoryId && ebonyId === ivoryId);
  }

  closeModal() {
    this.dialogRef.close();
  }

  // @ts-ignore
  setErrorMessage(err) {
    return this.errorMessage = err.error?.message || 'An unexpected error has occurred. Please try again later.';
  }
}
