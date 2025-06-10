import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
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
import {ChessMatchWithNames} from '../model/ChessMatchWithNames';
import {MAT_DIALOG_DATA, MatDialogRef, MatDialogTitle} from '@angular/material/dialog';

@Component({
  selector: 'app-past-match-modal',
  imports: [
    MatDialogTitle,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderCellDef,
    MatCell,
    MatCellDef,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRow,
    MatRowDef
  ],
  templateUrl: './past-match-modal.component.html',
  styleUrl: './past-match-modal.component.css'
})
export class PastMatchModalComponent implements OnInit {
  chessMatchForm!: FormGroup;
  errorMessage: string = '';
  displayError: boolean = false;

  displayedColumns: string[] = ['ebonyName', 'ivoryName', 'datePlayed', 'winner']
  dataSource = new MatTableDataSource<ChessMatchWithNames>();

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: {
      matches: ChessMatchWithNames[]
    },
    private dialogRef: MatDialogRef<PastMatchModalComponent>,
    private builder: FormBuilder
  ) {
  }

  ngOnInit() {
    if (this.data.matches.length === 0) {
      this.displayError = true;
      this.errorMessage = 'No matches to display';

    } else {
      this.displayError = false;
      this.dataSource.data = this.data.matches;
    }
  }

  closeModal() {
    this.dialogRef.close();
  }
}
