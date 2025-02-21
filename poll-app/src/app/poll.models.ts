export interface OptionVote{
    optionText: string;
    voteCount: number;
}

export interface Poll {
    id?: number; // Make id optional
    question: string;
    options: OptionVote[];
}
