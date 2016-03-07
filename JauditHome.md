# Introduction #

Welcome to the JAudit project.  This project is intended to standardize the components needed to implement a robust and useful auditing framework for Java and other applications.  This is NOT a logging library.  This is a library for defining higher level business events that need to be tracked for:
  * Compliance
  * Security
  * Metering


# Details #

Within this project you will find several components that build upon each other.  The following is a list of the concepts and a link to their content.

  * JauditBase : This is where the basic interfaces and structure of audit logs are definied.
  * JauditDao : This is where the basic interfaces for the DAO layer are defined which interact with/persist audit logs.
  * JauditVO : This is an implementation of the interfaces defined in JauditBase that are JPA annotated.
  * JauditDaoHibernate : A hibernate 3.x implementation of the JauditDao interfaces.