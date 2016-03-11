package net.karolek.neoguilds.utils;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashSet;
import java.util.Set;

public final class SpaceUtil {

    private SpaceUtil() {
    }

    private static double lengthSq(double x, double y, double z) {
        return (x * x) + (y * y) + (z * z);
    }

    private static double lengthSq(double x, double z) {
        return (x * x) + (z * z);
    }

    public Set<Location> makeCylinder(Location center, double radius, int height, boolean filled) {
        return makeCylinder(center, radius, radius, height, filled);
    }

    public Set<Location> makeCylinder(Location center, double radiusX, double radiusZ, int height, boolean filled) {

        Set<Location> locations = new HashSet<>();

        Point pos = new Point(center);
        radiusX += 0.5;
        radiusZ += 0.5;

        final double invRadiusX = 1 / radiusX;
        final double invRadiusZ = 1 / radiusZ;

        final int ceilRadiusX = (int) Math.ceil(radiusX);
        final int ceilRadiusZ = (int) Math.ceil(radiusZ);

        double nextXn = 0;
        forX:
        for (int x = 0; x <= ceilRadiusX; ++x) {
            final double xn = nextXn;
            nextXn = (x + 1) * invRadiusX;
            double nextZn = 0;
            forZ:
            for (int z = 0; z <= ceilRadiusZ; ++z) {
                final double zn = nextZn;
                nextZn = (z + 1) * invRadiusZ;

                double distanceSq = lengthSq(xn, zn);
                if (distanceSq > 1) {
                    if (z == 0) {
                        break forX;
                    }
                    break forZ;
                }

                if (!filled) {
                    if (lengthSq(nextXn, zn) <= 1 && lengthSq(xn, nextZn) <= 1) {
                        continue;
                    }
                }

                for (int y = 0; y < height; ++y) {
                    locations.add(pos.add(x, y, z).toLocation(center.getWorld()));
                    locations.add(pos.add(-x, y, z).toLocation(center.getWorld()));
                    locations.add(pos.add(x, y, -z).toLocation(center.getWorld()));
                    locations.add(pos.add(-x, y, -z).toLocation(center.getWorld()));
                }
            }
        }

        return locations;
    }

    public Set<Location> makeSphere(Location center, double radius, boolean filled) {
        return makeSphere(center, radius, radius, radius, filled);
    }

    public Set<Location> makeSphere(Location center, double radiusX, double radiusY, double radiusZ, boolean filled) {

        Set<Location> locations = new HashSet<>();
        Point pos = new Point(center);


        radiusX += 0.5;
        radiusY += 0.5;
        radiusZ += 0.5;

        final double invRadiusX = 1 / radiusX;
        final double invRadiusY = 1 / radiusY;
        final double invRadiusZ = 1 / radiusZ;

        final int ceilRadiusX = (int) Math.ceil(radiusX);
        final int ceilRadiusY = (int) Math.ceil(radiusY);
        final int ceilRadiusZ = (int) Math.ceil(radiusZ);

        double nextXn = 0;
        forX:
        for (int x = 0; x <= ceilRadiusX; ++x) {
            final double xn = nextXn;
            nextXn = (x + 1) * invRadiusX;
            double nextYn = 0;
            forY:
            for (int y = 0; y <= ceilRadiusY; ++y) {
                final double yn = nextYn;
                nextYn = (y + 1) * invRadiusY;
                double nextZn = 0;
                forZ:
                for (int z = 0; z <= ceilRadiusZ; ++z) {
                    final double zn = nextZn;
                    nextZn = (z + 1) * invRadiusZ;

                    double distanceSq = lengthSq(xn, yn, zn);
                    if (distanceSq > 1) {
                        if (z == 0) {
                            if (y == 0) {
                                break forX;
                            }
                            break forY;
                        }
                        break forZ;
                    }

                    if (!filled) {
                        if (lengthSq(nextXn, yn, zn) <= 1 && lengthSq(xn, nextYn, zn) <= 1 && lengthSq(xn, yn, nextZn) <= 1) {
                            continue;
                        }
                    }

                    locations.add(pos.add(x, y, z).toLocation(center.getWorld()));
                    locations.add(pos.add(-x, y, z).toLocation(center.getWorld()));
                    locations.add(pos.add(x, -y, z).toLocation(center.getWorld()));
                    locations.add(pos.add(x, y, -z).toLocation(center.getWorld()));
                    locations.add(pos.add(-x, -y, -z).toLocation(center.getWorld()));
                    locations.add(pos.add(x, -y, -z).toLocation(center.getWorld()));
                    locations.add(pos.add(-x, y, -z).toLocation(center.getWorld()));
                    locations.add(pos.add(-x, -y, z).toLocation(center.getWorld()));

                }
            }
        }

        return locations;
    }

    public Set<Location> makePyramid(Location center, int size, boolean filled) {

        Set<Location> locations = new HashSet<>();
        Point pos = new Point(center);

        int height = size;

        for (int y = 0; y <= height; ++y) {
            size--;
            for (int x = 0; x <= size; ++x) {
                for (int z = 0; z <= size; ++z) {
                    if ((filled && z <= size && x <= size) || z == size || x == size) {
                        locations.add(pos.add(x, y, z).toLocation(center.getWorld()));
                        locations.add(pos.add(-x, y, z).toLocation(center.getWorld()));
                        locations.add(pos.add(x, y, -z).toLocation(center.getWorld()));
                        locations.add(pos.add(-x, y, -z).toLocation(center.getWorld()));
                    }
                }
            }
        }

        return locations;
    }

    @Getter
    public static class Point {
        private final int x, y, z;

        public Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Point(Location location) {
            this.x = location.getBlockX();
            this.y = location.getBlockY();
            this.z = location.getBlockZ();
        }

        public Point add(int x, int y, int z) {
            return new Point(getX() + x, getY() + y, getZ() + z);
        }

        public Location toLocation(World world) {
            return new Location(world, x, y, z);
        }
    }


}
