import {Component} from '@angular/core';
import {Leaderboard} from './leaderboard';
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
    MatColumnDef
  ],
  templateUrl: './leaderboard.component.html',
  styleUrl: './leaderboard.component.css'
})
export class LeaderboardComponent {

  displayedColumns: string[] = ['ranking', 'playerName', 'gamesPlayed'];

  leaderboard: Leaderboard[] = [
    {ranking: 1, playerName: 'Susan Stefano', gamesPlayed: 194},
    {ranking: 2, playerName: 'Stefanie Smith', gamesPlayed: 200},
    {ranking: 3, playerName: 'Stan Jones', gamesPlayed: 50},
  ];

  dataSource = new MatTableDataSource(this.leaderboard);

}
