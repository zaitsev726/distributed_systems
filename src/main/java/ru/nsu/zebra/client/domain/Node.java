package ru.nsu.zebra.client.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static ru.nsu.zebra.client.domain.NodeConfig.*;

public class Node {
    /* пространство имен */
    public final String namespace;

    /* кол-во размещенных репозиториев */
    private int numberOfRepositories;

    /* максимальный размер репозитория */
    public final int maxRepositoryAmount;

    /* размер блочного устройства */
    public final int diskSize;

    /* объем оперативной памяти */
    public final int amountOfRAM;

    /* кол-во CPU */
    public final int numberOfCpu;

    public static Node createNode(String namespace,
                                  int maxRepositoryAmount,
                                  int diskSize,
                                  int amountOfRAM,
                                  int numberOfCpu) {
        validateValue(maxRepositoryAmount, MIN_REPOSITORY_AMOUNT, MAX_REPOSITORY_AMOUNT);
        validateValue(diskSize, MIN_DISK_SIZE, MAX_DISK_SIZE);
        validateValue(amountOfRAM, MIN_AMOUNT_OF_RAM, MAX_AMOUNT_OF_RAM);
        validateValue(numberOfCpu, MIN_NUMBER_OF_CPU, MAX_NUMBER_OF_CPU);

        return new Node(namespace, maxRepositoryAmount, diskSize, amountOfRAM, numberOfCpu);
    }

    private static void validateValue(int value, int minValue, int maxValue) {
        if (value < minValue || value > maxValue) {
            throw new IllegalArgumentException("Can't create Node with value " + value +
                    ". It should be in range (" + minValue + ":" + maxValue + ")");
        }
    }

    private Node(String namespace,
                 int maxRepositoryAmount,
                 int diskSize,
                 int amountOfRAM,
                 int numberOfCpu) {
        numberOfRepositories = 0;
        this.namespace = namespace;
        this.maxRepositoryAmount = maxRepositoryAmount;
        this.diskSize = diskSize;
        this.amountOfRAM = amountOfRAM;
        this.numberOfCpu = numberOfCpu;
    }

    public int getNumberOfRepositories() {
        return numberOfRepositories;
    }

    public boolean canFitRepository() {
        if (numberOfRepositories == maxRepositoryAmount)
            return false;
        return 2 * maxRepositoryAmount * numberOfRepositories < diskSize;
    }

    public void increaseNumberOfRepositories() {
        if (numberOfRepositories == maxRepositoryAmount) {
            throw new IllegalArgumentException("Current count " + numberOfRepositories + " is equals to max repository amount.");
        }
        numberOfRepositories++;
    }

    public BigDecimal getNodeScore() {
        if (!canFitRepository())
            return BigDecimal.ONE.negate();
        return BigDecimal.valueOf(
                        calculateUniformDistributionValue(maxRepositoryAmount, MIN_REPOSITORY_AMOUNT, MAX_REPOSITORY_AMOUNT) +
                        calculateUniformDistributionValue(diskSize, MIN_DISK_SIZE, MAX_DISK_SIZE) +
                        calculateUniformDistributionValue(amountOfRAM, MIN_AMOUNT_OF_RAM, MAX_AMOUNT_OF_RAM) +
                        calculateUniformDistributionValue(numberOfCpu, MIN_NUMBER_OF_CPU, MAX_NUMBER_OF_CPU))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private double calculateUniformDistributionValue(int currentValue, int minValue, int maxValue) {
        return ((double) currentValue - minValue) / (maxValue - minValue);
    }
}
