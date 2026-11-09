package dto;

public class TaskStatsDTO {
	private int total;
	private int notStartedCount;
	private int notStartedPercent;
	private int inProgressCount;
	private int inProgressPercent;
	private int completedCount;
	private int completedPercent;

	public TaskStatsDTO() {
	}

	public TaskStatsDTO(int notStartedCount, int inProgressCount, int completedCount) {
		this.notStartedCount = notStartedCount;
		this.inProgressCount = inProgressCount;
		this.completedCount = completedCount;
		this.total = notStartedCount + inProgressCount + completedCount;

		if (total > 0) {
			this.notStartedPercent = (int) Math.round(((double) notStartedCount / total) * 100);
			this.inProgressPercent = (int) Math.round(((double) inProgressCount / total) * 100);
			this.completedPercent = 100 - this.notStartedPercent - this.inProgressPercent;
			if (this.completedPercent < 0) this.completedPercent = 0;
		} else {
			this.notStartedPercent = 0;
			this.inProgressPercent = 0;
			this.completedPercent = 0;
		}
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getNotStartedCount() {
		return notStartedCount;
	}

	public void setNotStartedCount(int notStartedCount) {
		this.notStartedCount = notStartedCount;
	}

	public int getNotStartedPercent() {
		return notStartedPercent;
	}

	public void setNotStartedPercent(int notStartedPercent) {
		this.notStartedPercent = notStartedPercent;
	}

	public int getInProgressCount() {
		return inProgressCount;
	}

	public void setInProgressCount(int inProgressCount) {
		this.inProgressCount = inProgressCount;
	}

	public int getInProgressPercent() {
		return inProgressPercent;
	}

	public void setInProgressPercent(int inProgressPercent) {
		this.inProgressPercent = inProgressPercent;
	}

	public int getCompletedCount() {
		return completedCount;
	}

	public void setCompletedCount(int completedCount) {
		this.completedCount = completedCount;
	}

	public int getCompletedPercent() {
		return completedPercent;
	}

	public void setCompletedPercent(int completedPercent) {
		this.completedPercent = completedPercent;
	}
}

