package yourproject.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import yourproject.model.Reservation;

public class CustomTimelinePanel extends JPanel {
  private List<Reservation> reservations;
  private Consumer<Reservation> onReservationClickListener;

  // Stores the clickable bounding box for each drawn reservation bar
  private final Map<Rectangle, Reservation> barBounds = new HashMap<>();

  public CustomTimelinePanel(List<Reservation> reservations) {
    this.reservations = reservations;
    setPreferredSize(new Dimension(900, 400));
    setBackground(Color.WHITE);

    // Add mouse listener to detect clicks on custom drawn bars
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        Point clickPoint = e.getPoint();
        for (Map.Entry<Rectangle, Reservation> entry : barBounds.entrySet()) {
          if (entry.getKey().contains(clickPoint)) {
            if (onReservationClickListener != null) {
              onReservationClickListener.accept(entry.getValue());
            }
            break;
          }
        }
      }
    });
  }

  /**
   * Sets a callback listener to handle clicks on specific reservation bars.
   */
  public void setOnReservationClickListener(Consumer<Reservation> listener) {
    this.onReservationClickListener = listener;
  }

  public void updateData(List<Reservation> newReservations) {
    this.reservations = newReservations;
    repaint();
  }

  public void refreshData(List<Reservation> newReservations) {
    this.reservations = newReservations;
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    barBounds.clear(); // Clear previous click bounds on every repaint

    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    int width = getWidth();
    int height = getHeight();

    // Draw background grid lines & header
    g2d.setColor(Color.LIGHT_GRAY);
    g2d.drawLine(180, 50, 180, height - 30);
    g2d.drawLine(180, height - 30, width - 30, height - 30);

    g2d.setColor(Color.DARK_GRAY);
    g2d.setFont(new Font("Arial", Font.BOLD, 14));
    g2d.drawString("Equipment Timeline Schedule", 20, 25);

    if (reservations == null || reservations.isEmpty()) {
      g2d.drawString("No reservations to display.", 180, 90);
      return;
    }

    long now = System.currentTimeMillis();
    long minTime = Long.MAX_VALUE;
    long maxTime = Long.MIN_VALUE;

    Map<String, List<Reservation>> reservationsByItem = new LinkedHashMap<>();

    for (Reservation res : reservations) {
      if (res.getStartDate() != null) {
        long startTime = res.getStartDate().getTime();
        long endTime = (res.getEndDate() != null) ? res.getEndDate().getTime() : now;

        minTime = Math.min(minTime, startTime);
        maxTime = Math.max(maxTime, endTime);

        String equipmentKey = String.valueOf(res.getEquipmentId());
        reservationsByItem.computeIfAbsent(equipmentKey, k -> new ArrayList<>()).add(res);
      }
    }

    if (minTime == Long.MAX_VALUE || maxTime == Long.MIN_VALUE || minTime == maxTime) {
      minTime = now;
      maxTime = now + (7L * 24 * 60 * 60 * 1000);
    }

    long totalTimeSpan = maxTime - minTime;
    int timelineStartX = 180;
    int timelineWidth = width - 220;

    g2d.setFont(new Font("Arial", Font.PLAIN, 10));
    g2d.drawString(new Date(minTime).toString(), timelineStartX, height - 15);
    g2d.drawString(new Date(maxTime).toString(), width - 180, height - 15);

    int y = 70;
    for (Map.Entry<String, List<Reservation>> entry : reservationsByItem.entrySet()) {
      String equipmentId = entry.getKey();
      List<Reservation> itemReservations = entry.getValue();

      g2d.setColor(Color.BLACK);
      g2d.setFont(new Font("Arial", Font.PLAIN, 12));
      g2d.drawString("Item: " + equipmentId, 20, y + 15);

      for (Reservation res : itemReservations) {
        if (res.getStartDate() == null)
          continue;

        long startTime = res.getStartDate().getTime();
        long endTime = (res.getEndDate() != null) ? res.getEndDate().getTime() : now;

        double startRatio = (double) (startTime - minTime) / totalTimeSpan;
        double durationRatio = (double) (endTime - startTime) / totalTimeSpan;

        int barX = timelineStartX + (int) (startRatio * timelineWidth);
        int barWidth = Math.max(15, (int) (durationRatio * timelineWidth));

        // Create and store the bounding box for hit-testing
        Rectangle barRect = new Rectangle(barX, y, barWidth, 20);
        barBounds.put(barRect, res);

        boolean isActive = (res.getEndDate() == null);
        g2d.setColor(isActive ? new Color(46, 139, 87) : new Color(70, 130, 180));
        g2d.fillRoundRect(barRect.x, barRect.y, barRect.width, barRect.height, 8, 8);

        g2d.setColor(Color.WHITE);
        String label = res.getDescription() != null ? res.getDescription() : (isActive ? "Active" : "Reserved");
        g2d.drawString(label, barX + 5, y + 15);
      }

      y += 35;
      if (y > height - 50)
        break;
    }
  }
}
