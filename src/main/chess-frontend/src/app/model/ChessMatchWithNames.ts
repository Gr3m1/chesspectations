import {ChessMatch} from './ChessMatch';

export interface ChessMatchWithNames extends ChessMatch {
  ebonyName: string;
  ivoryName: string;
}
