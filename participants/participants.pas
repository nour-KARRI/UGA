//=========================================================================
//                      Participant model
//-------------------------------------------------------------------------
// This glossary model is expressed in ParticipantScript
// See https://modelscript.rtfd.io/en/latest/scripts/participants/index.html
//=========================================================================

participant model MyTheatre
import glossary model from "../glossaries/glossaries.gls"

//--- team roles ------------------------------------------------------

team role Communiquant
    | Le communiquant est charge de transmettre les informations utiles
	  | au reste du groupe

team role Developer
    | A developer is responsible to design, develop, test and
    | maintain models and pieces of code.

team role QualityManager
    | The QualityManager is responsible to define, with other
    | members of the development team, `QA` standard.
    | She also monitors `QC` process although she can to delegate
    | actual controls to other team members.

team role QualityMaster < QualityManager
    | A `QualityMaster` has all duties and privileges of
    |`QualityManager` but she also has the power to change
    | the content of `QA` and `QC` standard.

team role ScrumMaster
    | The `ScrumMaster` is the team role responsible for
    | ensuring the team lives agile values and principles and
    | follows the processes and practices that the team
    | agreed they would use.
    | The responsibilities of this role include:
    | * clearing obstacles,
    | * Establishing an environment where the team can be effective
    | * Addressing team dynamics
    | * Ensuring a good relationship between the team and
    |   product owner as well as others outside the team
    | Protecting the team from outside interruptions and distractions.

team role ProductOwner
    | The `ProductOwner` responsibility is to have a vision of
    | what she wishes to build, and convey that vision to the
    | `ScrumTeam`.


//=========================================================================
//   "Instance" level participants
//-------------------------------------------------------------------------
// Both personae and persons are at the instance level: they belong to
// one of many participant class (actor, stakeholder or team role)
// Personae are fictional characters that serve as instance of actors.
// Persons are real-life people.
//=========================================================================

----------------------------------------------------------------------
-- actor
----------------------------------------------------------------------

actor Spectateur
    | Un `Spectateur` a pour but de voir des `Spectacle` en achetant un
    | ou plusieurs `Tickets` pour une `Representation` depuis 'MyTheatre'.


----------------------------------------------------------------------
-- personas
----------------------------------------------------------------------

persona Paul : Spectateur
    name : "Paul"
    portrait :
    | Paul sort peu et ne connait probablement pas ce Theatre.

persona Janine : Spectateur
    name : "Janine"
    portrait :
    | Janine est déjà venue souvent dans ce Theatre.

----------------------------------------------------------------------
-- team
----------------------------------------------------------------------
person youssef : Communiquant, Developer
    name : "Youssef Bannouni"
    trigram : YBI

person nourredine : Developer,QualityManager
	  name : "Nourredine Karri"
	  trigram : NKI

person marjorie : Developer,QualityManager
	  name : "Marjorie Di Bin"
    trigram : MBN

person antoine : Developer
	  name : "Antoine Barel"
	  trigram : ABL
