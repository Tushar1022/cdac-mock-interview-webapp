//package app.solopdf.com.entity;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.time.LocalDate;
//
//@Setter
//@Getter
//@Entity
//@Table(name = "evaluations")
//public class Evaluation {
//    // Getters and Setters
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private LocalDate date;
//    private String candidateName;
//    private String rollNumber;
//
//    // Technical scores getters/setters
//    // Technical scores
//    private Integer oopsJava;
//    private Integer dataStructures;
//    private Integer databases;
//    private Integer j2ee;
//    private Integer webTechnologies;
//    private Integer msNet;
//    private Integer cppProgramming;
//
//    // Technical comments getters/setters
//    // Technical comments
//    private String oopsJavaComments;
//    private String dataStructuresComments;
//    private String databasesComments;
//    private String j2eeComments;
//    private String webTechnologiesComments;
//    private String msNetComments;
//    private String cppProgrammingComments;
//
//    // General scores getters/setters
//    // General scores
//    private Integer communication;
//    private Integer confidence;
//    private Integer attitude;
//    private Integer abilityToLearn;
//
//    // General comments getters/setters
//    // General comments
//    private String communicationComments;
//    private String confidenceComments;
//    private String attitudeComments;
//    private String abilityToLearnComments;
//
//    private String interviewSummary;
//    private String finalDecision;
//    private String interviewerName;
//
//    // Constructors
//    public Evaluation() {}
//
//}

package app.solopdf.com.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "evaluations")
public class Evaluation {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "candidate_name", nullable = false, length = 100)
    private String candidateName;

    @Column(name = "roll_number", nullable = false, length = 50)
    private String rollNumber;

    // Technical Scores (1-5)
    @Column(name = "oops_java", nullable = false)
    private Integer oopsJava = 0;

    @Column(name = "data_structures", nullable = false)
    private Integer dataStructures = 0;

    @Column(name = "database_technologies", nullable = false)
    private Integer databasesTechnologies = 0;

    @Column(name = "j2ee", nullable = false)
    private Integer j2ee = 0;

    @Column(name = "web_technologies", nullable = false)
    private Integer webTechnologies = 0;

    @Column(name = "ms_net", nullable = false)
    private Integer msNet = 0;

    @Column(name = "cpp_programming", nullable = false)
    private Integer cppProgramming = 0;

    // Technical Comments
    @Column(name = "oops_java_comments", length = 1000)
    private String oopsJavaComments;

    @Column(name = "data_structures_comments", length = 1000)
    private String dataStructuresComments;

    @Column(name = "databases_comments", length = 1000)
    private String databasesComments;

    @Column(name = "j2ee_comments", length = 1000)
    private String j2eeComments;

    @Column(name = "web_technologies_comments", length = 1000)
    private String webTechnologiesComments;

    @Column(name = "ms_net_comments", length = 1000)
    private String msNetComments;

    @Column(name = "cpp_programming_comments", length = 1000)
    private String cppProgrammingComments;

    // General Scores (1-5)
    @Column(name = "communication", nullable = false)
    private Integer communication = 0;

    @Column(name = "confidence", nullable = false)
    private Integer confidence = 0;

    @Column(name = "attitude", nullable = false)
    private Integer attitude = 0;

    @Column(name = "ability_to_learn", nullable = false)
    private Integer abilityToLearn = 0;

    // General Comments
    @Column(name = "communication_comments", length = 1000)
    private String communicationComments;

    @Column(name = "confidence_comments", length = 1000)
    private String confidenceComments;

    @Column(name = "attitude_comments", length = 1000)
    private String attitudeComments;

    @Column(name = "ability_to_learn_comments", length = 1000)
    private String abilityToLearnComments;

    @Column(name = "interview_summary", length = 2000)
    private String interviewSummary;

    @Column(name = "final_decision", nullable = false, length = 20)
    private String finalDecision;

    @Column(name = "interviewer_name", nullable = false, length = 100)
    private String interviewerName;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public Evaluation() {}

}