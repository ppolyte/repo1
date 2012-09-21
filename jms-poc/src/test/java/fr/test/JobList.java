package fr.test;

import java.util.TreeSet;
import java.util.Comparator;
import java.util.Set;

public class JobList {
	private static final long serialVersionUID = 1L;

	// for testing
	public static void populateJobs(Set<Job> jobSet) {
		jobSet.add(new Job(1, 1, 1));
		jobSet.add(new Job(2, 2, 1));
		jobSet.add(new Job(3, 3, 1));
		jobSet.add(new Job(4, 1, 2));
		jobSet.add(new Job(5, 2, 2));
		jobSet.add(new Job(6, 3, 2));
		jobSet.add(new Job(7, 1, 3));
		jobSet.add(new Job(8, 2, 3));
		jobSet.add(new Job(9, 3, 3));
		jobSet.add(new Job(10, 3, 4));
	}

	public static void main(String[] args) {
		TreeSet<Job> jobSet = new TreeSet<Job>();
		populateJobs(jobSet);
		System.out.println("Printing all the jobs sorted using job id");
		System.out.println(jobSet);
		System.out.println("Get the list of all jobs whose job id is < 5");
		Set set = jobSet.headSet(new Job(5, 0, 0));
		System.out.println(set);
		// We use a PriorityComparator
		jobSet = new TreeSet<Job>(new PriorityComparator());
		populateJobs(jobSet);
		System.out
				.println("Printing all the jobs sorted using PriorityComparator");
		System.out.println(jobSet);
		// Get the list of all jobs whose job id whose priority is low (1)
		System.out
				.println("Get the list of all jobs whose job id whose priority is low(1)");
		set = jobSet.subSet(Job.LOW_PRIORITY_JOB, Job.MEDIUM_PRIORITY_JOB);
		System.out.println(set);
		// We use a StatusComparator
		jobSet = new TreeSet<Job>(new StatusComparator());
		populateJobs(jobSet);
		System.out
				.println("Printing all the jobs sorted using StatusComparator");
		System.out.println(jobSet);
		// Get the list of all jobs that are inprogess(2) or completed(3)
		System.out
				.println("Printing all the jobs that are inprogress(2) or completed(3)");
		set = jobSet.subSet(Job.INPROGESS_JOB, Job.FAILED_JOB);
		System.out.println(set);
	}
}

class Job implements Comparable<Job> {
	public int id;
	public int priority; // low(1), medium(2), high(3)
	public int status; // Pending(1),InProgress(2), Completed(3), Failed(4)
	public static final Job LOW_PRIORITY_JOB = new Job(0, 1, 0);
	public static final Job MEDIUM_PRIORITY_JOB = new Job(0, 2, 0);
	public static final Job HIGH_PRIORITY_JOB = new Job(0, 3, 0);
	public static final Job PENDING_JOB = new Job(0, 0, 1);
	public static final Job INPROGESS_JOB = new Job(0, 0, 2);
	public static final Job COMPLETED_JOB = new Job(0, 0, 3);
	public static final Job FAILED_JOB = new Job(0, 0, 4);

	public Job(int id, int priority, int status) {
		this.id = id;
		this.priority = priority;
		this.status = status;
	}

	public String toString() {
		return "Job id->" + id + " priority->" + priority + " status->"
				+ status;
	}

	public int compareTo(Job job2) {
		return this.id - job2.id;
	}

	public boolean equals(Job job2) {
		return this.id == job2.id;
	}
}

class PriorityComparator implements Comparator<Job> {
	public int compare(Job job1, Job job2) {
		if (job1.priority == job2.priority) {
			return job1.id - job2.id;
		}
		return job1.priority - job2.priority;
	}
}

class StatusComparator implements Comparator<Job> {
	public int compare(Job job1, Job job2) {
		if (job1.status == job2.status) {
			return job1.id - job2.id;
		}
		return job1.status - job2.status;
	}
}