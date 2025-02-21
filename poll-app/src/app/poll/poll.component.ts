import { Component, OnInit } from '@angular/core';
import { PollService } from '../poll.service';
import { Poll } from '../poll.models';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-poll',
  imports: [CommonModule, FormsModule],
  templateUrl: './poll.component.html',
  styleUrl: './poll.component.css'
})
export class PollComponent implements OnInit{
  newPoll: Poll = {
    id: 0,
    question: '',
    options: [
      { optionText: '', voteCount: 0},
      { optionText: '', voteCount: 0}
    ]
  };

  polls: Poll[] =[];

  constructor(private pollService: PollService){

  }

  ngOnInit(): void {
    this.loadPolls();
  }

  loadPolls () {
    this.pollService.getPolls().subscribe({
      next: (data) => {
        this.polls = data;
      },
      error: (error) => {
        console.error("Error fetching polls: ", error);
      }
    });
  }

  resetPoll() {
    this.newPoll = {
      id: 0,
      question: '',
      options: [
        { optionText: '', voteCount: 0},
        { optionText: '', voteCount: 0}
      ]
    };
  }

  createPoll() {
    const payload = {
      question: this.newPoll.question,
      options: this.newPoll.options.map(opt => ({ optionText: opt.optionText }))
    };

    this.pollService.createPoll(this.newPoll).subscribe({
      next: (createdPoll) => {
        this.polls.push(createdPoll);
        this.resetPoll();
      },
      error: (error) => {
        console.error("Error fetching polls for create poll: ", error);
      }
    });
  }

  trackByIndex(index: number): number{
    return index;
  }
}
