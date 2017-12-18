package com.browne.spring.model;

public class Policy {
	
	private String level1a = "Procedure: The lecturer should identify potential sanctions based on Level 1 ITB Policy and update the Incidents details by clicking the \"update incident\" button below. Then use the form below to upload proof of plagiarism to the head of department. The lecturer may decide to raise the plagiarism level to 2 at their discretion ";
	private String level1b = "Procedure: Material has been uploaded and the appropriate head of department has been notified. The lecturer will recieve a notification via email when the head of department has reviewed the material";
	private String level1c = "Procedure: Plagiarism has been confirmed. The student should be informed formally, in writing, by the lecturer and sanctions should be imposed. The lecturer should detail the sanction imposed in the field \"Incident Sanction\". Please indicate \"Sanction Accepted\" and the Incident status field, \"Closed\" if the student has accepted sanctions or \"Appealed\" if there is to be an enquiry";
	private String level1d = "Procedure: The Incident is closed. No further action is required";
	private String level2a = "Procedure: The lecturer should identify potential sanctions based on Level 2 ITB Policy and then use the upload button below to upload proof of plagiarism to the appropriate class tutor or course coordinator. If they are the class tutor and course co-ordinator then please request the assistance of another tutor/coordinator in accordance with ITB policy ";
	private String level2b = "Procedure: Material has been uploaded and the appropriate class tutor or course coordinator has been notified. The lecturer should update \"plagiarism confirmed\" based on the Tutor/Coordinator response.";
	private String level2c = "Procedure: Plagiarism has been confirmed. The tutor or coordinator should request that the student attend a formal meeting to discuss the issues involved. The lecturer should record the outcomes of \"meeting attended\", \"sanction accepted\" and \"student uncooperative\" fields below. You may close the incident using the \"Incident Status\" fields and choose \"Closed\"";
	private String level2d = "Procedure: The Incident is closed. No further action is required";
	private String level3d = "Procedure: The Incident is closed. No further action is required";
	private String level3e = "Procedure: The student has denied plagiarism at level 2. The head of department must determine whether to apply level 1 or level 2 sanctions";
	private String level3f = "Procedure: The student failed to engage with process, failed to attend the level 2 meeting or the head of department cannot resolve the matter and it is now being referred to the registrar with potential level 4 disciplinary procedures";
	
	public String getPolicy(String stage) {
		if (stage.equals("1a")) {
			return getLevel1a();
		} else if (stage.equals("1b")) {
			return getLevel1b();
		} else if (stage.equals("1c")) {
			return getLevel1c();
		} else if (stage.equals("1d")) {
			return getLevel1d();
		} else if (stage.equals("2a")) {
			return getLevel2a();
		} else if (stage.equals("2b")) {
			return getLevel2b();
		} else if (stage.equals("2c")) {
			return getLevel2c();
		} else if (stage.equals("2d")) {
			return getLevel2d();
		} else if (stage.equals("3d")) {
			return getLevel3d();
		} else if (stage.equals("3e")) {
			return getLevel3e();
		} else if (stage.equals("3f")) {
			return getLevel3f();
		} else {
			return null;
		}
		
	}
	
	public String getLevel1a() {
		return level1a;
	}
	public String getLevel1b() {
		return level1b;
	}
	public String getLevel1c() {
		return level1c;
	}
	public String getLevel1d() {
		return level1d;
	}
	public String getLevel2a() {
		return level2a;
	}
	public String getLevel2b() {
		return level2b;
	}
	public String getLevel2c() {
		return level2c;
	}
	public String getLevel2d() {
		return level2d;
	}
	public String getLevel3d() {
		return level3d;
	}
	public String getLevel3e() {
		return level3e;
	}
	public String getLevel3f() {
		return level3f;
	}

	
}
