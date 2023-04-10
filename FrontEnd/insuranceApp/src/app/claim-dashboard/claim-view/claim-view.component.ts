import { Component } from '@angular/core';
import { ClaimserviceService } from '../claimservice.service';
import { Claim } from '../Model/Claim';
import { ClaimDocument } from '../Model/ClaimDocument';
import {MatSnackBar} from '@angular/material/snack-bar';
import { TemplateRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-claim-view',
  templateUrl: './claim-view.component.html',
  styleUrls: ['./claim-view.component.css']
})
export class ClaimViewComponent {

  newDocument: ClaimDocument = {
    documentId: '',
    url: '',
    information: ''
  };

  claims: Claim[] = [];
  showUnapproved: boolean = false;
  panelOpenState = false;
  role = sessionStorage.getItem("role");
  Userid = sessionStorage.getItem("Userid");

  constructor(private claimservice:ClaimserviceService, public snackBar: MatSnackBar, public dialog:MatDialog){}

  ngOnInit(): void{
    
    // this.claimservice.getClaims().subscribe(
    //   data => {
    //     this.claims = data;
    //   }
    // )

    if(this.role != null && this.Userid != null){

      if(this.role === "client"){

        this.claimservice.getClaimsByCustomer(this.Userid).subscribe(
          data => {
            this.claims = data;
          }
        )
  
      }else if(this.role === "company"){
  
        this.claimservice.getClaimsByCompany(this.Userid).subscribe(
          data => {
            this.claims = data;
          }
        )

      }

    }else{

      this.claimservice.getClaims().subscribe(
        data => {
          this.claims = data;
        }
      )
    }

  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 2000,
    });
  }

  deleteDocument(claim: Claim, claimDoc: ClaimDocument) {
    
    this.claimservice.deleteClaimDocument(claim.claimId, claimDoc.documentId).subscribe(
      data=>{
        
        console.log(data);
          if (claim.documents) {
            claim.documents = claim.documents.filter(cd => cd.documentId !== claimDoc.documentId);
          }
          console.log('After:', claim.documents);    
      },

      err => console.log(err)
    )
    if (claim.documents) {
      claim.documents = claim.documents.filter(cd => cd.documentId !== claimDoc.documentId);
    }
    
  }

  approveClaim(claim:Claim): void{
    
    this.claimservice.approveClaim(claim.claimId, !claim.isApproved + "").subscribe(
      data=>{
        console.log(data);
        claim.isApproved = !claim.isApproved;
        
      },

      err => console.log(err)
    )
    
  }

  get filteredClaims(){
    if(this.showUnapproved){
      return this.claims.filter((claim) => claim.isApproved === false);
    }else{
      return this.claims;
    }
  }

  
  openAddDocumentDialog(claim: Claim, addDocumentDialog: TemplateRef<any>): void {
    const dialogRef = this.dialog.open(addDocumentDialog, {
      width: '400px'
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const newDocumentToAdd: ClaimDocument = {
          documentId: this.newDocument.documentId,
          url: this.newDocument.url,
          information: this.newDocument.information,
          date: new Date().toISOString().substring(0, 10)
        };
  
        this.claimservice.addDocument(claim.claimId, newDocumentToAdd).subscribe(
          data => {
  
          },
          err => console.log(err)
        );
        if (!claim.documents) {
          claim.documents = [];
        }
  
        claim.documents.push(newDocumentToAdd);
  
        this.newDocument = {
          documentId: '',
          url: '',
          information: ''
        };
      }
    });
  }
  
  
  
  
  


}
