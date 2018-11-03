package com.company.modules.repay.domain.arithmetic;

//罚息方式
public enum PenaltyInterestType {
    remainingCapital,       //按剩余本金罚息
    remainingInterest,      //按剩余利息罚息
    CapitalAndInterest      //按剩余本息罚息
}
