/*
A town is building a watchtower. The watchtower is located at (0, 0). 
Each unit height of the watchtower has a cost H. There are N houses located at (x, y) coordinates. 
Each house will pay cost C if it comes under the surveillance of the watchtower. 
The horizontal distance covered by the watchtower is the same as it's height. 
Find out the max profit you can make.

Inputs:

N number of houses
list of (x, y) coordinates
H cost to build unit height
C cost each house pays the watchtower

*/

// O(nlogn)

double maxProfit(vector<pair<double, double>> &houses, double H, double C) {
    vector<double> distances;
    for (auto &house : houses) {
        double d = sqrt(house.first * house.first + house.second * house.second);
        distances.push_back(d);
    }

    sort(distances.begin(), distances.end());

    double max_profit = 0;

    for (int i = 0; i < distances.size() ; i++) {
        double h = distances[i]; 
        int count = upper_bound(distances.begin(), distances.end(), h) - distances.begin();

        double profit = count * C - h * H;
        max_profit = max(max_profit, profit);
    }

    return max_profit;
}
