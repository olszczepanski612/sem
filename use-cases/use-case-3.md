# USE CASE: 3 Produce a report on the salary of employees in my department

## CHARACTERISTIC INFORMATION

### Goal in Context

As a department manager I want to produce a report on the salary of employees in my department so that I can support financial reporting for my department.

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the department. Database contains employee and department

### Success End Condition

A report is available for department manager to provide to finance.

### Failed End Condition

No report is produced.

### Primary Actor

Department Manager

### Trigger

A request for finance information is sent to department manager.

## MAIN SUCCESS SCENARIO

1. Finance request salary information for a given role.
2. Department Manager extracts salary information for employees in their department
3. Department Manager provides report to finance.

## EXTENSIONS

1. **Role does not exist**:
    1. Department Manager informs finance no role exists.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
